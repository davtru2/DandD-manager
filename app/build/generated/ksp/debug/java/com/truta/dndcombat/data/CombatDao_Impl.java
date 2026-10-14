package com.truta.dndcombat.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CombatDao_Impl implements CombatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Combatant> __insertionAdapterOfCombatant;

  private final EntityInsertionAdapter<EnemyTemplate> __insertionAdapterOfEnemyTemplate;

  private final EntityDeletionOrUpdateAdapter<Combatant> __deletionAdapterOfCombatant;

  private final EntityDeletionOrUpdateAdapter<EnemyTemplate> __deletionAdapterOfEnemyTemplate;

  private final EntityDeletionOrUpdateAdapter<Combatant> __updateAdapterOfCombatant;

  private final SharedSQLiteStatement __preparedStmtOfClearCombat;

  public CombatDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCombatant = new EntityInsertionAdapter<Combatant>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `combatants` (`id`,`name`,`isPlayer`,`maxHp`,`currentHp`,`tempHp`,`ac`,`initiative`,`conditionsJson`,`resistancesJson`,`attacksJson`,`notes`,`isConcentrating`,`deathSaveSuccesses`,`deathSaveFailures`,`isDead`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Combatant entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final int _tmp = entity.isPlayer() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getMaxHp());
        statement.bindLong(5, entity.getCurrentHp());
        statement.bindLong(6, entity.getTempHp());
        statement.bindLong(7, entity.getAc());
        statement.bindLong(8, entity.getInitiative());
        statement.bindString(9, entity.getConditionsJson());
        statement.bindString(10, entity.getResistancesJson());
        statement.bindString(11, entity.getAttacksJson());
        statement.bindString(12, entity.getNotes());
        final int _tmp_1 = entity.isConcentrating() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindLong(14, entity.getDeathSaveSuccesses());
        statement.bindLong(15, entity.getDeathSaveFailures());
        final int _tmp_2 = entity.isDead() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
      }
    };
    this.__insertionAdapterOfEnemyTemplate = new EntityInsertionAdapter<EnemyTemplate>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `enemy_templates` (`id`,`name`,`maxHp`,`ac`,`resistancesJson`,`attacksJson`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EnemyTemplate entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getMaxHp());
        statement.bindLong(4, entity.getAc());
        statement.bindString(5, entity.getResistancesJson());
        statement.bindString(6, entity.getAttacksJson());
      }
    };
    this.__deletionAdapterOfCombatant = new EntityDeletionOrUpdateAdapter<Combatant>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `combatants` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Combatant entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfEnemyTemplate = new EntityDeletionOrUpdateAdapter<EnemyTemplate>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `enemy_templates` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EnemyTemplate entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCombatant = new EntityDeletionOrUpdateAdapter<Combatant>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `combatants` SET `id` = ?,`name` = ?,`isPlayer` = ?,`maxHp` = ?,`currentHp` = ?,`tempHp` = ?,`ac` = ?,`initiative` = ?,`conditionsJson` = ?,`resistancesJson` = ?,`attacksJson` = ?,`notes` = ?,`isConcentrating` = ?,`deathSaveSuccesses` = ?,`deathSaveFailures` = ?,`isDead` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Combatant entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final int _tmp = entity.isPlayer() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getMaxHp());
        statement.bindLong(5, entity.getCurrentHp());
        statement.bindLong(6, entity.getTempHp());
        statement.bindLong(7, entity.getAc());
        statement.bindLong(8, entity.getInitiative());
        statement.bindString(9, entity.getConditionsJson());
        statement.bindString(10, entity.getResistancesJson());
        statement.bindString(11, entity.getAttacksJson());
        statement.bindString(12, entity.getNotes());
        final int _tmp_1 = entity.isConcentrating() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindLong(14, entity.getDeathSaveSuccesses());
        statement.bindLong(15, entity.getDeathSaveFailures());
        final int _tmp_2 = entity.isDead() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfClearCombat = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM combatants";
        return _query;
      }
    };
  }

  @Override
  public Object insertCombatant(final Combatant combatant,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCombatant.insertAndReturnId(combatant);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTemplate(final EnemyTemplate template,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEnemyTemplate.insertAndReturnId(template);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCombatant(final Combatant combatant,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCombatant.handle(combatant);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTemplate(final EnemyTemplate template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEnemyTemplate.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCombatant(final Combatant combatant,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCombatant.handle(combatant);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearCombat(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearCombat.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearCombat.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Combatant>> getAllCombatants() {
    final String _sql = "SELECT * FROM combatants ORDER BY initiative DESC, id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"combatants"}, new Callable<List<Combatant>>() {
      @Override
      @NonNull
      public List<Combatant> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsPlayer = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlayer");
          final int _cursorIndexOfMaxHp = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHp");
          final int _cursorIndexOfCurrentHp = CursorUtil.getColumnIndexOrThrow(_cursor, "currentHp");
          final int _cursorIndexOfTempHp = CursorUtil.getColumnIndexOrThrow(_cursor, "tempHp");
          final int _cursorIndexOfAc = CursorUtil.getColumnIndexOrThrow(_cursor, "ac");
          final int _cursorIndexOfInitiative = CursorUtil.getColumnIndexOrThrow(_cursor, "initiative");
          final int _cursorIndexOfConditionsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionsJson");
          final int _cursorIndexOfResistancesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "resistancesJson");
          final int _cursorIndexOfAttacksJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attacksJson");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsConcentrating = CursorUtil.getColumnIndexOrThrow(_cursor, "isConcentrating");
          final int _cursorIndexOfDeathSaveSuccesses = CursorUtil.getColumnIndexOrThrow(_cursor, "deathSaveSuccesses");
          final int _cursorIndexOfDeathSaveFailures = CursorUtil.getColumnIndexOrThrow(_cursor, "deathSaveFailures");
          final int _cursorIndexOfIsDead = CursorUtil.getColumnIndexOrThrow(_cursor, "isDead");
          final List<Combatant> _result = new ArrayList<Combatant>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Combatant _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final boolean _tmpIsPlayer;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlayer);
            _tmpIsPlayer = _tmp != 0;
            final int _tmpMaxHp;
            _tmpMaxHp = _cursor.getInt(_cursorIndexOfMaxHp);
            final int _tmpCurrentHp;
            _tmpCurrentHp = _cursor.getInt(_cursorIndexOfCurrentHp);
            final int _tmpTempHp;
            _tmpTempHp = _cursor.getInt(_cursorIndexOfTempHp);
            final int _tmpAc;
            _tmpAc = _cursor.getInt(_cursorIndexOfAc);
            final int _tmpInitiative;
            _tmpInitiative = _cursor.getInt(_cursorIndexOfInitiative);
            final String _tmpConditionsJson;
            _tmpConditionsJson = _cursor.getString(_cursorIndexOfConditionsJson);
            final String _tmpResistancesJson;
            _tmpResistancesJson = _cursor.getString(_cursorIndexOfResistancesJson);
            final String _tmpAttacksJson;
            _tmpAttacksJson = _cursor.getString(_cursorIndexOfAttacksJson);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsConcentrating;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsConcentrating);
            _tmpIsConcentrating = _tmp_1 != 0;
            final int _tmpDeathSaveSuccesses;
            _tmpDeathSaveSuccesses = _cursor.getInt(_cursorIndexOfDeathSaveSuccesses);
            final int _tmpDeathSaveFailures;
            _tmpDeathSaveFailures = _cursor.getInt(_cursorIndexOfDeathSaveFailures);
            final boolean _tmpIsDead;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsDead);
            _tmpIsDead = _tmp_2 != 0;
            _item = new Combatant(_tmpId,_tmpName,_tmpIsPlayer,_tmpMaxHp,_tmpCurrentHp,_tmpTempHp,_tmpAc,_tmpInitiative,_tmpConditionsJson,_tmpResistancesJson,_tmpAttacksJson,_tmpNotes,_tmpIsConcentrating,_tmpDeathSaveSuccesses,_tmpDeathSaveFailures,_tmpIsDead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EnemyTemplate>> getAllTemplates() {
    final String _sql = "SELECT * FROM enemy_templates ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"enemy_templates"}, new Callable<List<EnemyTemplate>>() {
      @Override
      @NonNull
      public List<EnemyTemplate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMaxHp = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHp");
          final int _cursorIndexOfAc = CursorUtil.getColumnIndexOrThrow(_cursor, "ac");
          final int _cursorIndexOfResistancesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "resistancesJson");
          final int _cursorIndexOfAttacksJson = CursorUtil.getColumnIndexOrThrow(_cursor, "attacksJson");
          final List<EnemyTemplate> _result = new ArrayList<EnemyTemplate>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EnemyTemplate _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpMaxHp;
            _tmpMaxHp = _cursor.getInt(_cursorIndexOfMaxHp);
            final int _tmpAc;
            _tmpAc = _cursor.getInt(_cursorIndexOfAc);
            final String _tmpResistancesJson;
            _tmpResistancesJson = _cursor.getString(_cursorIndexOfResistancesJson);
            final String _tmpAttacksJson;
            _tmpAttacksJson = _cursor.getString(_cursorIndexOfAttacksJson);
            _item = new EnemyTemplate(_tmpId,_tmpName,_tmpMaxHp,_tmpAc,_tmpResistancesJson,_tmpAttacksJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
