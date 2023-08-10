// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.finalteamproject.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPhoneBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cvDone;

  @NonNull
  public final CardView cvSubmit;

  @NonNull
  public final EditText edtNumber;

  @NonNull
  public final EditText edtPhone;

  @NonNull
  public final ImageView imgvBack;

  @NonNull
  public final RelativeLayout rlMessage;

  @NonNull
  public final TextView tvMessage;

  @NonNull
  public final TextView tvReissue;

  @NonNull
  public final TextView tvTimer;

  @NonNull
  public final TextView tvWrong;

  private ActivityPhoneBinding(@NonNull LinearLayout rootView, @NonNull CardView cvDone,
      @NonNull CardView cvSubmit, @NonNull EditText edtNumber, @NonNull EditText edtPhone,
      @NonNull ImageView imgvBack, @NonNull RelativeLayout rlMessage, @NonNull TextView tvMessage,
      @NonNull TextView tvReissue, @NonNull TextView tvTimer, @NonNull TextView tvWrong) {
    this.rootView = rootView;
    this.cvDone = cvDone;
    this.cvSubmit = cvSubmit;
    this.edtNumber = edtNumber;
    this.edtPhone = edtPhone;
    this.imgvBack = imgvBack;
    this.rlMessage = rlMessage;
    this.tvMessage = tvMessage;
    this.tvReissue = tvReissue;
    this.tvTimer = tvTimer;
    this.tvWrong = tvWrong;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPhoneBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPhoneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_phone, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPhoneBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cv_done;
      CardView cvDone = ViewBindings.findChildViewById(rootView, id);
      if (cvDone == null) {
        break missingId;
      }

      id = R.id.cv_submit;
      CardView cvSubmit = ViewBindings.findChildViewById(rootView, id);
      if (cvSubmit == null) {
        break missingId;
      }

      id = R.id.edt_number;
      EditText edtNumber = ViewBindings.findChildViewById(rootView, id);
      if (edtNumber == null) {
        break missingId;
      }

      id = R.id.edt_phone;
      EditText edtPhone = ViewBindings.findChildViewById(rootView, id);
      if (edtPhone == null) {
        break missingId;
      }

      id = R.id.imgv_back;
      ImageView imgvBack = ViewBindings.findChildViewById(rootView, id);
      if (imgvBack == null) {
        break missingId;
      }

      id = R.id.rl_message;
      RelativeLayout rlMessage = ViewBindings.findChildViewById(rootView, id);
      if (rlMessage == null) {
        break missingId;
      }

      id = R.id.tv_message;
      TextView tvMessage = ViewBindings.findChildViewById(rootView, id);
      if (tvMessage == null) {
        break missingId;
      }

      id = R.id.tv_reissue;
      TextView tvReissue = ViewBindings.findChildViewById(rootView, id);
      if (tvReissue == null) {
        break missingId;
      }

      id = R.id.tv_timer;
      TextView tvTimer = ViewBindings.findChildViewById(rootView, id);
      if (tvTimer == null) {
        break missingId;
      }

      id = R.id.tv_wrong;
      TextView tvWrong = ViewBindings.findChildViewById(rootView, id);
      if (tvWrong == null) {
        break missingId;
      }

      return new ActivityPhoneBinding((LinearLayout) rootView, cvDone, cvSubmit, edtNumber,
          edtPhone, imgvBack, rlMessage, tvMessage, tvReissue, tvTimer, tvWrong);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
