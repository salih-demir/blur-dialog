package com.cascade.blurdialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class BlurDialog extends DialogFragment {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 6.5f;

    private Drawable dialogBackground;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            buildDialogBackground(activity);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        buildDialogBackground(activity);
    }

    private void buildDialogBackground(Activity activity) {
        final View viewToBlur = activity.findViewById(android.R.id.content).getRootView();
        viewToBlur.post(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    Resources resources = getResources();
                    Bitmap bitmap = BitmapUtil.getViewVisual(viewToBlur);
                    if (resources != null && bitmap != null) {
                        Bitmap bitmapBlurred = BitmapUtil.blurBitmap(getActivity(), bitmap, BITMAP_SCALE, BLUR_RADIUS);

                        Drawable[] drawables = new Drawable[2];
                        drawables[0] = new BitmapDrawable(getResources(), bitmapBlurred);
                        drawables[1] = new ColorDrawable(Color.argb(100, 0, 0, 0));

                        dialogBackground = new LayerDrawable(drawables);
                        setBackground();
                    }
                }
            }
        });
    }

    private void setBackground() {
        if (dialogBackground != null) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    window.setBackgroundDrawable(dialogBackground);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            }
        }
    }
}