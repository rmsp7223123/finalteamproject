// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.finalteamproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemGameRankBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView gameId;

  @NonNull
  public final TextView gameRank;

  @NonNull
  public final TextView gameScore;

  private ItemGameRankBinding(@NonNull LinearLayout rootView, @NonNull TextView gameId,
      @NonNull TextView gameRank, @NonNull TextView gameScore) {
    this.rootView = rootView;
    this.gameId = gameId;
    this.gameRank = gameRank;
    this.gameScore = gameScore;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemGameRankBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemGameRankBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_game_rank, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemGameRankBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.game_id;
      TextView gameId = ViewBindings.findChildViewById(rootView, id);
      if (gameId == null) {
        break missingId;
      }

      id = R.id.game_rank;
      TextView gameRank = ViewBindings.findChildViewById(rootView, id);
      if (gameRank == null) {
        break missingId;
      }

      id = R.id.game_score;
      TextView gameScore = ViewBindings.findChildViewById(rootView, id);
      if (gameScore == null) {
        break missingId;
      }

      return new ItemGameRankBinding((LinearLayout) rootView, gameId, gameRank, gameScore);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
