package com.snakybo.snakemp.common.screen;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.utils.Bounds;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.client.player.SnakePart;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Config;

public class ScreenGame extends Screen {
	public ScreenGame() {
		super(false);
	}
	
	@Override
	public void render(Renderer renderer) {
		super.render(renderer);
		
		glColor3f(0.15294117647f, 0.34901960784f, 0);
		
		for(int x = 1; x < Config.GRID_WIDTH_HEIGHT.x; x++) {
			glPushMatrix(); {
				glLoadIdentity();
				
				glTranslatef(x * Config.GRID_SIZE, 0, 0);
				
				glBegin(GL_LINES); {
					glVertex2f(0, 0);
					glVertex2f(0, Config.GRID_WIDTH_HEIGHT.y * Config.GRID_SIZE);
				} glEnd();
			} glPopMatrix();
		}
		
		for(int y = 1; y < Config.GRID_WIDTH_HEIGHT.y; y++) {
			glPushMatrix(); {
				glLoadIdentity();
				
				glTranslatef(0, y * Config.GRID_SIZE, 0);
				
				glBegin(GL_LINES); {
					glVertex2f(0, 0);
					glVertex2f(Config.GRID_WIDTH_HEIGHT.x * Config.GRID_SIZE, 0);
				} glEnd();
			} glPopMatrix();
		}
		
		ClientData[] clients = SnakeMultiplayer.getInstance().getClient().getClientList().getClients();
		
		for(ClientData client : clients) {
			if(client != null) {
				for(SnakePart part : client.getParts()) {
					Bounds bounds = new Bounds(
								part.getX() * Config.GRID_SIZE,
								part.getX() * Config.GRID_SIZE + Config.GRID_SIZE,
								part.getY() * Config.GRID_SIZE,
								part.getY() * Config.GRID_SIZE + Config.GRID_SIZE
							);
					
					renderer.drawQuad(bounds, client.getColor());
					
					//System.out.println(part.getX() + " " + part.getY());
				}
				
				/*Bounds headPos = new Bounds(
							(client.getPosition().x) * Config.GRID_SIZE,
							(client.getPosition().x * Config.GRID_SIZE) + Config.GRID_SIZE,
							(client.getPosition().y) * Config.GRID_SIZE,
							(client.getPosition().y * Config.GRID_SIZE) + Config.GRID_SIZE
						);
				
				renderer.drawQuad(headPos, client.getColor());
				
				System.out.println(headPos.left + " " + headPos.right + " " + headPos.top + " " + headPos.bottom);
				*/
				/*Bounds bounds = new Bounds(
					client.getPosition().x,
					client.getPosition().x + Config.GRID_SIZE,
					client.getPosition().y,
					client.getPosition().y + Config.GRID_SIZE
				);
				
				renderer.drawQuad(bounds, client.getColor());
				
				for(SnakePart part : client.getParts()) {
//					System.out.println(part.getX() + " " + part.getY());
					
					Bounds partBounds = new Bounds(
						bounds.left - (part.getX() * Config.GRID_SIZE),
						bounds.right - (part.getX() * Config.GRID_SIZE),
						bounds.top - (part.getY() * Config.GRID_SIZE),
						bounds.bottom -= (part.getY() * Config.GRID_SIZE)
					);
					
					renderer.drawQuad(partBounds, client.getColor());
				}*/
			}
		}
	}
}
