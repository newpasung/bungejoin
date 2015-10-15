package com.season.bungejoin.bungejoin.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.season.bungejoin.bungejoin.utils.DensityUtils;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Administrator on 2015/10/12.
 */
public class JBoxView extends View {
    World world;
    Paint paint;
    Body body;
    public JBoxView(Context context) {
        super(context);
        Vec2 gravity =new Vec2(0f,10f);
        world =new World(gravity,true);
        CircleShape circleShape=new CircleShape();
        circleShape.m_radius=20;
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=2f;
        fixtureDef.friction=0.2f;
        fixtureDef.restitution=0.95f;
        fixtureDef.shape=circleShape;
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(200f/10,200f/10);
        bodyDef.type= BodyType.DYNAMIC;
        bodyDef.linearVelocity=new Vec2(0,10f);
        body=world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        PolygonShape polygonShape =new PolygonShape();
        polygonShape.setAsBox(DensityUtils.getWindowWidth((Activity)getContext()),40);
        FixtureDef fixtureDef1=new FixtureDef();
        fixtureDef1.shape=polygonShape;
        fixtureDef1.restitution=0.95f;
        fixtureDef1.friction=0.5f;
        fixtureDef1.density=2f;
        BodyDef bodyDef1=new BodyDef();
        bodyDef.position.set(DensityUtils.getWindowWidth((Activity)getContext())/10,
                (DensityUtils.getWindowHeight((Activity)getContext())-DensityUtils.getStatusBarHei((Activity)getContext()))/10);
        world.createBody(bodyDef1).createFixture(fixtureDef1);
        paint=new Paint();
    }

    public void refresh(){
        world.step(1 / 60f, 10, 8);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(body.getPosition().x*10,body.getPosition().y*10,20,paint);
    }
}
