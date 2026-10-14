# Rastreador de Combate D&D — versión Kotlin

Reescritura nativa en Kotlin (Jetpack Compose + Room) de la app que antes era
un único HTML/React. Pensada para abrirse directamente en Android Studio.

## Cómo abrirla

1. Descomprime el .zip.
2. Android Studio → **File → Open** → selecciona la carpeta `DndCombatTracker`.
3. Espera al "Gradle Sync". Como el proyecto no trae el `.jar` del Gradle
   Wrapper (no es texto, no se puede generar a mano), es posible que Android
   Studio te pida sincronizar o regenerar el wrapper la primera vez —
   acepta lo que te proponga (**Sync Project with Gradle Files** o el aviso
   de "Upgrade Assistant" si sugiere otra versión de AGP/Kotlin). También
   puede que te proponga actualizar `compileSdk`/AGP a lo último que tengas
   instalado; aceptarlo no debería romper nada.
4. Ejecuta en un emulador o dispositivo con el botón ▶.

## Paquete y nombre

- `applicationId` / paquete: `com.truta.dndcombat` (cámbialo desde Android
  Studio con *Refactor → Rename* si quieres otro).
- Nombre visible: "Rastreador de Combate D&D" (en `res/values/strings.xml`).

## Arquitectura

- **UI**: Jetpack Compose + Material 3, tema oscuro con acento carmesí (para
  alejarse del amarillo/dorado que pediste cambiar).
- **Estado**: un `CombatViewModel` con `StateFlow`, alimentado por Room a
  través de `Flow` — cualquier cambio se guarda solo, no hace falta botón de
  guardar.
- **Persistencia**: Room con dos tablas:
  - `combatants`: el combate activo (esto sustituye al guardado automático
    que ya tenía la versión HTML).
  - `enemy_templates`: las plantillas de enemigos reutilizables entre
    sesiones.
  - Las listas (ataques, condiciones, resistencias) se guardan como JSON
    dentro de cada fila para no complicar el esquema con tablas relacionadas.
- **2 pestañas**, igual que en la versión anterior: **Crear** (jugadores,
  enemigos y plantillas) y **Combate** (la iniciativa en curso).

## Qué funciones de la versión anterior están cubiertas

- Alta de jugadores y enemigos con vida, armadura e iniciativa.
- Vida/armadura/iniciativa ajustables (+/-, incluye botones ±5 de vida).
- Ataques/habilidades de enemigo con bono al golpe, daño y límite de usos.
- Condiciones/estados con duración en rondas (botón "Ronda siguiente" las
  descuenta y las quita al llegar a 0).
- Duplicar combatiente.
- Tiradas de salvación de muerte para jugadores a 0 de vida.
- Guardado automático del combate (vía Room).
- Vida temporal (absorbe daño antes que la vida normal).
- Resistencias en la creación de enemigos.
- Notas libres por combatiente.
- Concentración en hechizos (casilla).
- Plantillas de enemigos reutilizables guardadas entre sesiones.
- Botón para editar CA/vida desde la propia tarjeta y para reiniciar el
  combate entero.

## Lo que NO he incluido (para no meterme en cosas que no pediste)

- Icono propio de la app (usa el genérico de Android por ahora).
- Edición de nombre/iniciativa de un combatiente ya creado desde la tarjeta
  (sí puedes editar CA, vida, condiciones, notas y concentración).
- Tests automatizados.

Dímelo si quieres que añada alguna de estas u otra cosa.
