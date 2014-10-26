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

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.utils.Bounds;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.client.player.SnakePart;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.data.Config;

public class ScreenGame extends Screen {
	private ClientData[] clients;
	
	public ScreenGame() {
		super(false);
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(clients == null)
			clients = SnakeMP.getInstance().getClient().getClientList().getClients();
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
		
		for(ClientData client : clients) {
			if(client != null) {
				if(client.isAlive()) {				
					for(SnakePart part : client.getParts()) {
						Bounds bounds = new Bounds(
									part.getX() * Config.GRID_SIZE,
									part.getX() * Config.GRID_SIZE + Config.GRID_SIZE,
									part.getY() * Config.GRID_SIZE,
									part.getY() * Config.GRID_SIZE + Config.GRID_SIZE
								);
						
						renderer.drawQuad(bounds, client.getColor());
					}
				}
			}
		}
	}
}
