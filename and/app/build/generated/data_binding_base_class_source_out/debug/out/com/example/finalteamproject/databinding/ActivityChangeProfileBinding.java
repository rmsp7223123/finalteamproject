// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.finalteamproject.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityChangeProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnChangeNickname;

  @NonNull
  public final Button btnChangeProfile;

  @NonNull
  public final ImageView imgvBack;

  @NonNull
  public final CircleImageView imgvProfileimg;

  @NonNull
  public final TextView tvNickname;

  private ActivityChangeProfileBinding(@NonNull LinearLayout rootView,
      @NonNull Button btnChangeNickname, @NonNull Button btnChangeProfile,
      @NonNull ImageView imgvBack, @NonNull CircleImageView imgvProfileimg,
      @NonNull TextView tvNickname) {
    this.rootView = rootView;
    this.btnChangeNickname = btnChangeNickname;
    this.btnChangeProfile = btnChangeProfile;
    this.imgvBack = imgvBack;
    this.imgvProfileimg = imgvProfileimg;
    this.tvNickname = tvNickname;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityChangeProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityChangeProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_change_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityChangeProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_change_nickname;
      Button btnChangeNickname = ViewBindings.findChildViewById(rootView, id);
      if (btnChangeNickname == null) {
        break missingId;
      }

      id = R.id.btn_change_profile;
      Button btnChangeProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnChangeProfile == null) {
        break missingId;
      }

      id = R.id.imgv_back;
      ImageView imgvBack = ViewBindings.findChildViewById(rootView, id);
      if (imgvBack == null) {
        break missingId;
      }

      id = R.id.imgv_profileimg;
      CircleImageView imgvProfileimg = ViewBindings.findChildViewById(rootView, id);
      if (imgvProfileimg == null) {
        break missingId;
      }

      id = R.id.tv_nickname;
      TextView tvNickname = ViewBindings.findChildViewById(rootView, id);
      if (tvNickname == null) {
        break missingId;
      }

      return new ActivityChangeProfileBinding((LinearLayout) rootView, btnChangeNickname,
          btnChangeProfile, imgvBack, imgvProfileimg, tvNickname);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
