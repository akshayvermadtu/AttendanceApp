package com.akshayvermadtugmail.dtusmartattender;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

public class CachedFileProvider  extends ContentProvider {

    private static final String CLASS_NAME = "CachedFileProvider";
    public static final String AUTHORITY = "com.akshayvermadtugmail.dtusmartattender.provider";
    private UriMatcher uriMatcher;


    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "*", 1);

        return true;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {

        switch (uriMatcher.match(uri)) {
            case 1:
                String fileLocation = getContext().getCacheDir() + File.separator + uri.getLastPathSegment();
                ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(fileLocation), ParcelFileDescriptor.MODE_READ_ONLY);
                return pfd;

            default:
                throw new FileNotFoundException("Unsupported uri: " + uri.toString());
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
