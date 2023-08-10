// Generated by view binder compiler. Do not edit!
package com.example.finalteamproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public final class ItemMessageChatBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout containerFrame;

  @NonNull
  public final LinearLayout containerLinearMessageChat;

  @NonNull
  public final CardView cvMain;

  @NonNull
  public final ImageView imgvMain;

  @NonNull
  public final TextView tvName;

  private ItemMessageChatBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout containerFrame, @NonNull LinearLayout containerLinearMessageChat,
      @NonNull CardView cvMain, @NonNull ImageView imgvMain, @NonNull TextView tvName) {
    this.rootView = rootView;
    this.containerFrame = containerFrame;
    this.containerLinearMessageChat = containerLinearMessageChat;
    this.cvMain = cvMain;
    this.imgvMain = imgvMain;
    this.tvName = tvName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMessageChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMessageChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_message_chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMessageChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.container_frame;
      LinearLayout containerFrame = ViewBindings.findChildViewById(rootView, id);
      if (containerFrame == null) {
        break missingId;
      }

      id = R.id.container_linear_message_chat;
      LinearLayout containerLinearMessageChat = ViewBindings.findChildViewById(rootView, id);
      if (containerLinearMessageChat == null) {
        break missingId;
      }

      id = R.id.cv_main;
      CardView cvMain = ViewBindings.findChildViewById(rootView, id);
      if (cvMain == null) {
        break missingId;
      }

      id = R.id.imgv_main;
      ImageView imgvMain = ViewBindings.findChildViewById(rootView, id);
      if (imgvMain == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      return new ItemMessageChatBinding((LinearLayout) rootView, containerFrame,
          containerLinearMessageChat, cvMain, imgvMain, tvName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
