package isa3.cs262.calvin.edu.homework2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

class PlayerLoader extends AsyncTaskLoader<String> {
    private final String playerID;

    public PlayerLoader(@NonNull Context context, String qString) {
        super(context);
        playerID = qString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(playerID);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
