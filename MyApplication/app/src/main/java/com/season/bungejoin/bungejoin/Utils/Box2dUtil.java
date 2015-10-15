package com.season.bungejoin.bungejoin.utils;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Administrator on 2015/10/13.
 */
public class Box2dUtil {

    public static void addFallingBall(World world,float x,float y,float radius){
        CircleShape circleShape =new CircleShape();
        circleShape.m_radius=radius;
        FixtureDef fixtureDef =new FixtureDef();
        fixtureDef.density=2f;
        fixtureDef.friction=0.5f;
        fixtureDef.restitution=0.95f;
        fixtureDef.shape=circleShape;
        BodyDef bodyDef=new BodyDef();
        bodyDef.linearVelocity=new Vec2(0f,10f);
        bodyDef.type= BodyType.DYNAMIC;
        bodyDef.position.set(x,y);
        world.createBody(bodyDef).createFixture(fixtureDef);
    }

    public static void addHorPlate(World world,float w,float h,float x,float y){
        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(w,h);
        FixtureDef fixtureDef =new FixtureDef();
        fixtureDef.shape=polygonShape;
        fixtureDef.friction=0.3f;
        fixtureDef.density=1f;
        fixtureDef.restitution=0.8f;
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type=BodyType.DYNAMIC;
        world.createBody(bodyDef).createFixture(fixtureDef);
    }

}
