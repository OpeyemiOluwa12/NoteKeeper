package com.example.git.plurasightlearn1;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.git.plurasightlearn1.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.git.plurasightlearn1.NoteKeeperDatabaseContract.NoteInfoEntry;

import static com.example.git.plurasightlearn1.NoteKeeperProviderContract.*;

public class NoteKeeperProvider extends ContentProvider {
    public static final String MIME_VENDOR_TYPE = "vnd." + AUTHORITY;
    NoteKeeperOpenHelper mDbOpenHelper;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int COURSES = 0;
    public static final int NOTES = 1;

    public static final int NOTES_EXPANDED = 2;

    private static final int NOTES_ROW = 3;

    static {
        sUriMatcher.addURI(AUTHORITY, Courses.PATH, COURSES);
        sUriMatcher.addURI(AUTHORITY, Notes.PATH, NOTES);
        sUriMatcher.addURI(AUTHORITY, Notes.PATH_EXPANDED, NOTES_EXPANDED);
        sUriMatcher.addURI(AUTHORITY, Notes.PATH + "/#", NOTES_ROW);
    }

    public NoteKeeperProvider() {

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        String mimeType = null;
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case NOTES:
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH;
                break;
            case COURSES:
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Courses.PATH;
                break;
            case NOTES_EXPANDED:
                mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH_EXPANDED;
                break;
            case NOTES_ROW:
                mimeType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                        MIME_VENDOR_TYPE + Notes.PATH;
                break;
        }
        return mimeType;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        long rowId = -1;
        Uri rowUri = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                rowId = db.insert(NoteInfoEntry.TABLE_NAME, null, values);
                rowUri = ContentUris.withAppendedId(Notes.CONTENT_URI, rowId);
                break;
            case COURSES:
                rowId = db.insert(CourseInfoEntry.TABLE_NAME, null, values);
                rowUri = ContentUris.withAppendedId(Courses.CONTENT_URI, rowId);
                break;
        }
        return rowUri;
    }

    @Override
    public boolean onCreate() {
        mDbOpenHelper = new NoteKeeperOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case COURSES:
                cursor = db.query(CourseInfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case NOTES:
                cursor = db.query(NoteInfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case NOTES_EXPANDED:
                cursor = notesExpandedQuery(db, projection, selection, selectionArgs, sortOrder);
                break;
            case NOTES_ROW:
                long rowId = ContentUris.parseId(uri);
                String rowSelection = NoteInfoEntry._ID + " =?";
                String[] rowSelectionArgs = new String[]{Long.toString(rowId)};
                cursor = db.query(NoteInfoEntry.TABLE_NAME, projection, rowSelection, rowSelectionArgs, null, null, null);
                break;
        }
        return cursor;
    }

    private Cursor notesExpandedQuery(SQLiteDatabase db, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String[] columns = new String[projection.length];

        for (int id = 0; id < projection.length; id++) {
            columns[id] = projection[id].equals(BaseColumns._ID) ||
                    projection[id].equals(CoursesIdsColumns.COLUMN_COURSE_ID) ?
                    NoteInfoEntry.getQName(projection[id]) : projection[id];

        }

        String tablesWithJoin = NoteInfoEntry.TABLE_NAME + " JOIN " +
                CourseInfoEntry.TABLE_NAME + " ON " +
                NoteInfoEntry.getQName(NoteInfoEntry.COLUMN_COURSE_ID) + " = " +
                CourseInfoEntry.getQName(CourseInfoEntry.COLUMN_COURSE_ID);

        return db.query(tablesWithJoin, columns, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
