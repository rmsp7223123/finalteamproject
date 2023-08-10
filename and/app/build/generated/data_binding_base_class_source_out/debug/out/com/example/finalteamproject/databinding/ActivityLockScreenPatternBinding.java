// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.andrognito.patternlockview.PatternLockView;
import com.example.finalteamproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLockScreenPatternBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final PatternLockView patternLockView;

  private ActivityLockScreenPatternBinding(@NonNull LinearLayout rootView,
      @NonNull PatternLockView patternLockView) {
    this.rootView = rootView;
    this.patternLockView = patternLockView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLockScreenPatternBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLockScreenPatternBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_lock_screen_pattern, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLockScreenPatternBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.patternLockView;
      PatternLockView patternLockView = ViewBindings.findChildViewById(rootView, id);
      if (patternLockView == null) {
        break missingId;
      }

      return new ActivityLockScreenPatternBinding((LinearLayout) rootView, patternLockView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
