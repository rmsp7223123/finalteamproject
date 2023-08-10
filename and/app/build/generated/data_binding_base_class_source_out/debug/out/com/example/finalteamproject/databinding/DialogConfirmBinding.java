// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class DialogConfirmBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnNo;

  @NonNull
  public final Button btnYes;

  @NonNull
  public final TextView message;

  private DialogConfirmBinding(@NonNull LinearLayout rootView, @NonNull Button btnNo,
      @NonNull Button btnYes, @NonNull TextView message) {
    this.rootView = rootView;
    this.btnNo = btnNo;
    this.btnYes = btnYes;
    this.message = message;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogConfirmBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogConfirmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_confirm, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogConfirmBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnNo;
      Button btnNo = ViewBindings.findChildViewById(rootView, id);
      if (btnNo == null) {
        break missingId;
      }

      id = R.id.btnYes;
      Button btnYes = ViewBindings.findChildViewById(rootView, id);
      if (btnYes == null) {
        break missingId;
      }

      id = android.R.id.message;
      TextView message = ViewBindings.findChildViewById(rootView, id);
      if (message == null) {
        break missingId;
      }

      return new DialogConfirmBinding((LinearLayout) rootView, btnNo, btnYes, message);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
