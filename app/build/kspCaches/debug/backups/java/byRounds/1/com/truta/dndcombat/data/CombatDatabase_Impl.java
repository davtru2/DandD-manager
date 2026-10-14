package com.truta.dndcombat.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CombatDatabase_Impl extends CombatDatabase {
  private volatile CombatDao _combatDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `combatants` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `isPlayer` INTEGER NOT NULL, `maxHp` INTEGER NOT NULL, `currentHp` INTEGER NOT NULL, `tempHp` INTEGER NOT NULL, `ac` INTEGER NOT NULL, `initiative` INTEGER NOT NULL, `conditionsJson` TEXT NOT NULL, `resistancesJson` TEXT NOT NULL, `attacksJson` TEXT NOT NULL, `notes` TEXT NOT NULL, `isConcentrating` INTEGER NOT NULL, `deathSaveSuccesses` INTEGER NOT NULL, `deathSaveFailures` INTEGER NOT NULL, `isDead` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `enemy_templates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `maxHp` INTEGER NOT NULL, `ac` INTEGER NOT NULL, `resistancesJson` TEXT NOT NULL, `attacksJson` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e83b57898a94d6673465653fda5b189e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `combatants`");
        db.execSQL("DROP TABLE IF EXISTS `enemy_templates`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCombatants = new HashMap<String, TableInfo.Column>(16);
        _columnsCombatants.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("isPlayer", new TableInfo.Column("isPlayer", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("maxHp", new TableInfo.Column("maxHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("currentHp", new TableInfo.Column("currentHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("tempHp", new TableInfo.Column("tempHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("ac", new TableInfo.Column("ac", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("initiative", new TableInfo.Column("initiative", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("conditionsJson", new TableInfo.Column("conditionsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("resistancesJson", new TableInfo.Column("resistancesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("attacksJson", new TableInfo.Column("attacksJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("isConcentrating", new TableInfo.Column("isConcentrating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("deathSaveSuccesses", new TableInfo.Column("deathSaveSuccesses", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("deathSaveFailures", new TableInfo.Column("deathSaveFailures", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCombatants.put("isDead", new TableInfo.Column("isDead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCombatants = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCombatants = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCombatants = new TableInfo("combatants", _columnsCombatants, _foreignKeysCombatants, _indicesCombatants);
        final TableInfo _existingCombatants = TableInfo.read(db, "combatants");
        if (!_infoCombatants.equals(_existingCombatants)) {
          return new RoomOpenHelper.ValidationResult(false, "combatants(com.truta.dndcombat.data.Combatant).\n"
                  + " Expected:\n" + _infoCombatants + "\n"
                  + " Found:\n" + _existingCombatants);
        }
        final HashMap<String, TableInfo.Column> _columnsEnemyTemplates = new HashMap<String, TableInfo.Column>(6);
        _columnsEnemyTemplates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnemyTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnemyTemplates.put("maxHp", new TableInfo.Column("maxHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnemyTemplates.put("ac", new TableInfo.Column("ac", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnemyTemplates.put("resistancesJson", new TableInfo.Column("resistancesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnemyTemplates.put("attacksJson", new TableInfo.Column("attacksJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEnemyTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEnemyTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEnemyTemplates = new TableInfo("enemy_templates", _columnsEnemyTemplates, _foreignKeysEnemyTemplates, _indicesEnemyTemplates);
        final TableInfo _existingEnemyTemplates = TableInfo.read(db, "enemy_templates");
        if (!_infoEnemyTemplates.equals(_existingEnemyTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "enemy_templates(com.truta.dndcombat.data.EnemyTemplate).\n"
                  + " Expected:\n" + _infoEnemyTemplates + "\n"
                  + " Found:\n" + _existingEnemyTemplates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e83b57898a94d6673465653fda5b189e", "b7a9ed4229c10d79f2c0191b09ae2f97");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "combatants","enemy_templates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `combatants`");
      _db.execSQL("DELETE FROM `enemy_templates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CombatDao.class, CombatDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CombatDao combatDao() {
    if (_combatDao != null) {
      return _combatDao;
    } else {
      synchronized(this) {
        if(_combatDao == null) {
          _combatDao = new CombatDao_Impl(this);
        }
        return _combatDao;
      }
    }
  }
}
