package com.example.finalteamproject.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.example.finalteamproject.R;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private static final int BLANK = 0;
    private static final int PLAY = 1;
    private static final int DELAY = 1500;

    private int status;

    private ArrayList<Animal> arAnimal = new ArrayList<>();
    private Random rnd = new Random();
    private Activity mParent;

    private int[] animalImages = {
            R.drawable.animal1, R.drawable.animal2, R.drawable.animal3,
            R.drawable.animal4, R.drawable.animal5, R.drawable.animal6,
            R.drawable.animal7, R.drawable.animal8, R.drawable.animal9,
            R.drawable.animal10, R.drawable.animal11, R.drawable.animal12
    };

    public GameView(Context context) {
        super(context);
        mParent = (Activity) context;
        status = BLANK;

        handler.sendEmptyMessageDelayed(0, DELAY);
    }

    class Animal {
        Bitmap image;
        int x, y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        if (status == BLANK) {
            return;
        }

        for (Animal animal : arAnimal) {
            canvas.drawBitmap(animal.image, animal.x, animal.y, null);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            AddNewAnimal();
            status = PLAY;
            invalidate();

            String title = "stage ~ " + arAnimal.size();
            mParent.setTitle(title);
        }
    };

    public void AddNewAnimal() {
        Animal animal = new Animal();
        int idx;

        boolean bFindIntersect;
        Bitmap animalImage = BitmapFactory.decodeResource(getResources(), animalImages[rnd.nextInt(animalImages.length)]);

        while (true) {
            int size = rnd.nextInt(101) + 100;
            animal.x = rnd.nextInt(getWidth() - size);
            animal.y = rnd.nextInt(getHeight() - size);

            animal.image = Bitmap.createScaledBitmap(animalImage, size, size, false);

            bFindIntersect = false;

            for (Animal existingAnimal : arAnimal) {
                if (Rect.intersects(new Rect(animal.x, animal.y, animal.x + size, animal.y + size),
                        new Rect(existingAnimal.x, existingAnimal.y,
                                existingAnimal.x + existingAnimal.image.getWidth(),
                                existingAnimal.y + existingAnimal.image.getHeight()))) {
                    bFindIntersect = true;
                    break;
                }
            }

            if (!bFindIntersect) {
                break;
            }
        }

        arAnimal.add(animal);
    }

    int score = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int sel = findAnimalIdx((int) event.getX(), (int) event.getY());

            if (sel == -1) {
                return true;
            }

            if (sel == arAnimal.size() - 1) {
                score++;
                status = BLANK;
                invalidate();
                handler.sendEmptyMessageDelayed(0, DELAY);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(mParent);
                builder.setTitle(score+"점 달성!");
                builder.setMessage("다시 도전 하시겠어요?");
                builder.setNegativeButton("다시도전", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        score = 0;
                        arAnimal.clear();
                        status = BLANK;
                        invalidate();
                        handler.sendEmptyMessageDelayed(0, DELAY);
                    }
                });

                builder.setPositiveButton("그만할래요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mParent.finish();
                    }
                });

                builder.setCancelable(false);
                builder.show();
            }
        }

        return false;
    }

    public int findAnimalIdx(int x, int y) {
        for (int idx = 0; idx < arAnimal.size(); idx++) {
            Animal animal = arAnimal.get(idx);
            if (x >= animal.x && x <= animal.x + animal.image.getWidth() &&
                    y >= animal.y && y <= animal.y + animal.image.getHeight()) {
                return idx;
            }
        }
        return -1;
    }
}
