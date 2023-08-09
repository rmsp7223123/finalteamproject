package com.example.finalteamproject.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private static final int BLANK = 0; // 상태값 (대기상태)
    private static final int PLAY = 1; //게임이 진행중
    private static final int DELAY = 1500; // 도형 생성 시간 (1500 = 1.5초)

    private int status;

    private ArrayList<Shape> arShape = new ArrayList<Shape>();
    private Random rnd = new Random(); //난수값을 구하는 클래스
    private Activity mParent;

    public GameView(Context context){
        super(context);
        mParent = (Activity)context;
        status = BLANK;

        //핸들러 실행
        handler.sendEmptyMessageDelayed(0,DELAY);
    }

    //도형의 색상, 모양 등의 속성을 관리할수있는 중첩클래스 생성
    class Shape{
        static final int RECT = 0; // 사각형
        static final int CIRCLE = 1; //원
        static final int TRIANGLE = 2; //사각형

        int what;
        int color;
        Rect rt;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //캔버스를 검정색으로 두자
        canvas.drawColor(Color.BLACK);

        //대기상태 (BLANK(0)) 일때 리턴시키겠다.
        if(status == BLANK){
            return;
        }
        int idx = 0;

        for(idx = 0; idx < arShape.size(); idx++){
            Paint pnt = new Paint();

            pnt.setColor(arShape.get(idx).color);

            Rect rt = arShape.get(idx).rt;

            switch (arShape.get(idx).what){
                case Shape.RECT:
                    canvas.drawRect(rt,pnt);
                    break;
                case Shape.CIRCLE:
                    canvas.drawCircle
                            (rt.left+rt.width()/2,
                                    rt.top+rt.height()/2,
                                    rt.width()/2, pnt);
                    break;

                case Shape.TRIANGLE:
                    Path path = new Path();
                    path.moveTo(rt.left + rt.width()/2,rt.top);
                    path.lineTo(rt.left,rt.bottom);
                    path.lineTo(rt.right,rt.bottom);
                    canvas.drawPath(path,pnt);
                    break;
                default:
                    break;
            }
        }
    }

    //일정 간격으로 도형을 생성하기 위한 핸들러
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {

            AddNewShape();
            status = PLAY;
            invalidate();

            String title = "stage ~ " + arShape.size();
            mParent.setTitle(title);
        }
    };

    //새로운 도형을 추가하기 위한 메소드
    public void AddNewShape(){
        Shape shape = new Shape();
        int idx;

        //도형이 중복되었는지 판별하는 변수
        boolean bFindIntersect;

        Rect rt = new Rect();

        //랜덤으로 도형 사이즈 생성 - 50~150 사이의 난수를 생성해서 만든다.
        while (true){

            int size = rnd.nextInt(101)+50;

            //사각형의 범위
            rt.left = rnd.nextInt(getWidth());
            rt.top = rnd.nextInt(getHeight());
            rt.right = rt.left + size;
            rt.bottom = rt.top + size;

            //도형이 화면을 벗어날경우 새로 그리라는 메소드
            if(rt.right >= getWidth() || rt.bottom >= getHeight()){

                // if문 안에있는 케이스가 될 경우 continue 하라, 즉 처음부터 다시해라
                continue;
            }

            bFindIntersect = false;

            for(idx = 0; idx <arShape.size();idx++){
                if(rt.intersect(arShape.get(idx).rt)==true){

                    //겹치면 true
                    bFindIntersect = true;

                }
            }
            if(bFindIntersect == false){
                break;
            }
        }

        //도형 모양
        shape.what = rnd.nextInt(3);

        //도형 컬러 지정
        switch (rnd.nextInt(5)){
            case 0:
                shape.color = Color.WHITE;
                break;
            case 1:
                shape.color = Color.RED;
                break;
            case 2:
                shape.color = Color.GREEN;
                break;
            case 3:
                shape.color = Color.BLUE;
                break;
            case 4:
                shape.color = Color.MAGENTA;
                break;

        }
        shape.rt = rt;
        arShape.add(shape);

    }

    int score=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            //도형의 위치를 제대로 클릭했는지 판별하기 위한 메소드 호출
            int sel;

            sel = findShapeIdx((int)event.getX(),(int)event.getY());

            if(sel == -1){
                return true;
            }

            //완료가 되면 다음스테이지로 넘기겠다.
            if(sel == arShape.size() -1){
                score ++;
                status = BLANK;
                invalidate();
                handler.sendEmptyMessageDelayed(0,DELAY);

                //게임 종료시 alertDialog 생성
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(mParent);
                builder.setTitle(score+"점 달성!");
                builder.setMessage("다시 도전 하시겠어요?");
                builder.setNegativeButton("다시도전", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //게임 초기화
                        score = 0;
                        arShape.clear();
                        status = BLANK;
                        invalidate();
                        handler.sendEmptyMessageDelayed(0,DELAY);

                    }
                });

                builder.setPositiveButton("그만할래요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mParent.finish(); // 종료
                    }
                });

                builder.setCancelable(false);
                builder.show();

            }

        }

        return false;
    }

    public int findShapeIdx(int x,int y) {
        for (int idx = 0; idx < arShape.size(); idx++) {

            //arShape에 담긴 도형중에 터치된 x,y좌표를 가진 rect객체의 인덱스값을 반환
            if (arShape.get(idx).rt.contains(x, y)) { // contains? 객체가 있는지 없는지에 따라 명령
                return idx;
            }
        }
//        return findShapeIdx(x,y);
        return -1;
    }
}
