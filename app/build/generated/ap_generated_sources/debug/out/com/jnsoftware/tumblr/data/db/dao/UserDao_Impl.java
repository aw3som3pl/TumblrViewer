package com.jnsoftware.tumblr.data.db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jnsoftware.tumblr.data.db.model.User;
import com.jnsoftware.tumblr.data.utils.DateConverter;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser_1;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `user` (`uid`,`first_name`,`last_name`,`email`,`mobile`,`address`,`image_uri`,`last_update`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUid());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMobile());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImageUrl());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getLastUpdate());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateConverter.dateToTimestamp(value.getCreatedAt());
        if (_tmp_1 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp_1);
        }
      }
    };
    this.__insertionAdapterOfUser_1 = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `user` (`uid`,`first_name`,`last_name`,`email`,`mobile`,`address`,`image_uri`,`last_update`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUid());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMobile());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImageUrl());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getLastUpdate());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateConverter.dateToTimestamp(value.getCreatedAt());
        if (_tmp_1 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `user` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUid());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `user` SET `uid` = ?,`first_name` = ?,`last_name` = ?,`email` = ?,`mobile` = ?,`address` = ?,`image_uri` = ?,`last_update` = ?,`created_at` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUid());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMobile());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImageUrl());
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.getLastUpdate());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = DateConverter.dateToTimestamp(value.getCreatedAt());
        if (_tmp_1 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp_1);
        }
        stmt.bindLong(10, value.getUid());
      }
    };
  }

  @Override
  public void insertUser(final User mUser) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(mUser);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllUser(final User... mUsersList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser_1.insert(mUsersList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(final User mUser) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(mUser);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUser(final User mUser) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(mUser);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<User> getAll() {
    final String _sql = "SELECT * FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfMFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
      final int _cursorIndexOfMLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
      final int _cursorIndexOfMEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfMMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
      final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfMImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_uri");
      final int _cursorIndexOfMLastUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_update");
      final int _cursorIndexOfMCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpMFirstName;
        _tmpMFirstName = _cursor.getString(_cursorIndexOfMFirstName);
        final String _tmpMLastName;
        _tmpMLastName = _cursor.getString(_cursorIndexOfMLastName);
        final String _tmpMEmail;
        _tmpMEmail = _cursor.getString(_cursorIndexOfMEmail);
        final String _tmpMMobile;
        _tmpMMobile = _cursor.getString(_cursorIndexOfMMobile);
        final String _tmpMAddress;
        _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
        final Date _tmpMLastUpdate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMLastUpdate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMLastUpdate);
        }
        _tmpMLastUpdate = DateConverter.fromTimestamp(_tmp);
        final Date _tmpMCreatedAt;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMCreatedAt)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMCreatedAt);
        }
        _tmpMCreatedAt = DateConverter.fromTimestamp(_tmp_1);
        _item = new User(_tmpMFirstName,_tmpMLastName,_tmpMEmail,_tmpMMobile,_tmpMAddress,_tmpMLastUpdate,_tmpMCreatedAt);
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        final String _tmpMImageUrl;
        _tmpMImageUrl = _cursor.getString(_cursorIndexOfMImageUrl);
        _item.setImageUrl(_tmpMImageUrl);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User getUserById(final int uId) {
    final String _sql = "SELECT * FROM user WHERE uid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfMFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
      final int _cursorIndexOfMLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
      final int _cursorIndexOfMEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfMMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
      final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfMImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_uri");
      final int _cursorIndexOfMLastUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_update");
      final int _cursorIndexOfMCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final User _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMFirstName;
        _tmpMFirstName = _cursor.getString(_cursorIndexOfMFirstName);
        final String _tmpMLastName;
        _tmpMLastName = _cursor.getString(_cursorIndexOfMLastName);
        final String _tmpMEmail;
        _tmpMEmail = _cursor.getString(_cursorIndexOfMEmail);
        final String _tmpMMobile;
        _tmpMMobile = _cursor.getString(_cursorIndexOfMMobile);
        final String _tmpMAddress;
        _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
        final Date _tmpMLastUpdate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMLastUpdate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMLastUpdate);
        }
        _tmpMLastUpdate = DateConverter.fromTimestamp(_tmp);
        final Date _tmpMCreatedAt;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMCreatedAt)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMCreatedAt);
        }
        _tmpMCreatedAt = DateConverter.fromTimestamp(_tmp_1);
        _result = new User(_tmpMFirstName,_tmpMLastName,_tmpMEmail,_tmpMMobile,_tmpMAddress,_tmpMLastUpdate,_tmpMCreatedAt);
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _result.setUid(_tmpUid);
        final String _tmpMImageUrl;
        _tmpMImageUrl = _cursor.getString(_cursorIndexOfMImageUrl);
        _result.setImageUrl(_tmpMImageUrl);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> loadAllByIds(final int[] userIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT ");
    _stringBuilder.append("*");
    _stringBuilder.append(" FROM user WHERE uid IN (");
    final int _inputSize = userIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : userIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfMFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
      final int _cursorIndexOfMLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
      final int _cursorIndexOfMEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfMMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
      final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfMImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_uri");
      final int _cursorIndexOfMLastUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_update");
      final int _cursorIndexOfMCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item_1;
        final String _tmpMFirstName;
        _tmpMFirstName = _cursor.getString(_cursorIndexOfMFirstName);
        final String _tmpMLastName;
        _tmpMLastName = _cursor.getString(_cursorIndexOfMLastName);
        final String _tmpMEmail;
        _tmpMEmail = _cursor.getString(_cursorIndexOfMEmail);
        final String _tmpMMobile;
        _tmpMMobile = _cursor.getString(_cursorIndexOfMMobile);
        final String _tmpMAddress;
        _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
        final Date _tmpMLastUpdate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMLastUpdate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMLastUpdate);
        }
        _tmpMLastUpdate = DateConverter.fromTimestamp(_tmp);
        final Date _tmpMCreatedAt;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMCreatedAt)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMCreatedAt);
        }
        _tmpMCreatedAt = DateConverter.fromTimestamp(_tmp_1);
        _item_1 = new User(_tmpMFirstName,_tmpMLastName,_tmpMEmail,_tmpMMobile,_tmpMAddress,_tmpMLastUpdate,_tmpMCreatedAt);
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item_1.setUid(_tmpUid);
        final String _tmpMImageUrl;
        _tmpMImageUrl = _cursor.getString(_cursorIndexOfMImageUrl);
        _item_1.setImageUrl(_tmpMImageUrl);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User findByName(final String first, final String last) {
    final String _sql = "SELECT * FROM user WHERE first_name LIKE ? AND last_name LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (first == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, first);
    }
    _argIndex = 2;
    if (last == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, last);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfMFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "first_name");
      final int _cursorIndexOfMLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "last_name");
      final int _cursorIndexOfMEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfMMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
      final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfMImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_uri");
      final int _cursorIndexOfMLastUpdate = CursorUtil.getColumnIndexOrThrow(_cursor, "last_update");
      final int _cursorIndexOfMCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final User _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMFirstName;
        _tmpMFirstName = _cursor.getString(_cursorIndexOfMFirstName);
        final String _tmpMLastName;
        _tmpMLastName = _cursor.getString(_cursorIndexOfMLastName);
        final String _tmpMEmail;
        _tmpMEmail = _cursor.getString(_cursorIndexOfMEmail);
        final String _tmpMMobile;
        _tmpMMobile = _cursor.getString(_cursorIndexOfMMobile);
        final String _tmpMAddress;
        _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
        final Date _tmpMLastUpdate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMLastUpdate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMLastUpdate);
        }
        _tmpMLastUpdate = DateConverter.fromTimestamp(_tmp);
        final Date _tmpMCreatedAt;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMCreatedAt)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMCreatedAt);
        }
        _tmpMCreatedAt = DateConverter.fromTimestamp(_tmp_1);
        _result = new User(_tmpMFirstName,_tmpMLastName,_tmpMEmail,_tmpMMobile,_tmpMAddress,_tmpMLastUpdate,_tmpMCreatedAt);
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _result.setUid(_tmpUid);
        final String _tmpMImageUrl;
        _tmpMImageUrl = _cursor.getString(_cursorIndexOfMImageUrl);
        _result.setImageUrl(_tmpMImageUrl);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
