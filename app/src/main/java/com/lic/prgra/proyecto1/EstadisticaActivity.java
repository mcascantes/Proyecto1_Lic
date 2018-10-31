package com.lic.prgra.proyecto1;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.util.*;

import java.util.*;

public class EstadisticaActivity extends AppCompatActivity {

    public static final int SELECTED_SEGMENT_OFFSET = 50;

    private TextView TamanoPie;
    private SeekBar tamanoPieSeekBar;

    public PieChart pie;

    final Random mRandom = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);

        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("Gastos");


        pie=findViewById(R.id.GraficoPastel);

        pie.getLegend().setVisible(true);

        final float padding = PixelUtils.dpToPix(20);
        pie.getPie().setPadding(padding, padding, padding, padding);


        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPie().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);

                    if(segment != null) {
                        final boolean isSelected = getFormatter(segment).getOffset() != 0;
                        deselectAll();
                        setSelected(segment, !isSelected);
                        pie.redraw();
                    }
                }
                return false;
            }

            private SegmentFormatter getFormatter(Segment segment) {
                return pie.getFormatter(segment, PieRenderer.class);
            }

            private void deselectAll() {
                List<Segment> segments = pie.getRegistry().getSeriesList();
                for(Segment segment : segments) {
                    setSelected(segment, false);
                }
            }

            private void setSelected(Segment segment, boolean isSelected) {
                SegmentFormatter f = getFormatter(segment);
                if(isSelected) {
                    f.setOffset(SELECTED_SEGMENT_OFFSET);
                } else {
                    f.setOffset(0);
                }
            }
        });

       tamanoPieSeekBar=findViewById(R.id.BarraTamanoGrafico);
        tamanoPieSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                        PieRenderer.DonutMode.PERCENT);
                pie.redraw();
                updateDonutText();
            }
        });

        TamanoPie = findViewById(R.id.lblTamano);
        updateDonutText();

       /* s1 = new Segment("s1", 3);
        s2 = new Segment("s2", 1);
        s3 = new Segment("s3", 7);
        s4 = new Segment("s4", 9);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter(this, R.xml.pie_segment_formatter1);
        sf1.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter(this, R.xml.pie_segment_formatter2);
        sf2.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter(this, R.xml.pie_segment_formatter3);
        sf3.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter(Color.DKGRAY);
        sf4.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
        sf4.getFillPaint().setMaskFilter(emf);

        pie.addSegment(s1, sf1);
        pie.addSegment(s2, sf2);
        pie.addSegment(s3, sf3);
        pie.addSegment(s4, sf4);*/

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
           // System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            SegmentFormatter sf=new SegmentFormatter(ColorAleatorio());
            sf.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);
            sf.getFillPaint().setMaskFilter(emf);

            Segment s=new Segment(entry.getKey(),Porcentaje(hashMap,Double.parseDouble(entry.getValue())));
            pie.addSegment(s,sf);

        }

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);


    }

    private double Porcentaje(HashMap<String,String> h,double valor)
    {
        double total=0;

        for (Map.Entry<String, String> entry : h.entrySet()) {
            total=total + Double.parseDouble(entry.getValue());
        }

        return (valor/total)*100;

    }


    private int ColorAleatorio(){


          /*  float[] TEMP_HSL = new float[]{0, 0, 0};
            float[] hsl = TEMP_HSL;
            hsl[0] = (float) (Math.random() * 360);
            hsl[1] = (float) (40 + (Math.random() * 60));
            hsl[2] = (float) (40 + (Math.random() * 60));
            return ColorUtils.HSLToColor(hsl);*/
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + mRandom.nextInt(256)) / 2;
        final int green = (baseGreen + mRandom.nextInt(256)) / 2;
        final int blue = (baseBlue + mRandom.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);

    }


    private void updateDonutText()
    {
        TamanoPie.setText(tamanoPieSeekBar.getProgress() + "%");
    }

    @Override
    public void onStart() {
        super.onStart();
        setupIntroAnimation();
    }

    protected void setupIntroAnimation() {

        final PieRenderer renderer = pie.getRenderer(PieRenderer.class);
        // start with a zero degrees pie:

        renderer.setExtentDegs(0);
        // animate a scale value from a starting val of 0 to a final value of 1:
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // use an animation pattern that begins and ends slowly:
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = valueAnimator.getAnimatedFraction();
                renderer.setExtentDegs(360 * scale);
                pie.redraw();
            }
        });

        // the animation will run for 1.5 seconds:
        animator.setDuration(1500);
        animator.start();
    }


}
