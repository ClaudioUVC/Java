import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;

public class seguidor {
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		ColorSensor color = new ColorSensor(SensorPort.S2);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
		
			//LoopParaCrearColor
			int fila=0;
			int columna=0;
			int sentido=0;
			int[][] calles = new int[4][4];
			//calles[fila][columna]={sentido (-1,1,0)} 
			
			while(fila <= 3)
			{
				sentido=0;
				while(columna<=3)
				{
				//LimpiarPantalla
				LCD.clear();
				LCD.drawString("Light:",0,5);
				LCD.drawInt(light.getNormalizedLightValue(),7,5);
				if (color.getnormalizedColorValue()==rojo){
					sentido=-1;
				}else if(color.getnormalizedColorValue()==azul){
					sentido=1;
				}else if(color.getnormalizedColorValue==verde){
					sentido=0
				}
				calles[fila][columna]=sentido;
				contador++;
				}
			}
			//valor negro final
			int promedio = (negro[0] + negro[1] + negro[2] + negro[3] + negro[4] + negro[5] + negro[6] + negro[7] + negro[8] + negro[9])/10;
			int pa = promedio + 225;
			int pb = promedio - 225;
			//LimpiarPantalla
			LCD.clear();
			//Mostrar valor de negro en LCD x=0 y=0
			LCD.drawString("Negro",0,0);
			LCD.drawInt(promedio,2,1);
			//Mostrar valor de Ancho
			int v0 = 350;
			int va = 300;
			int vb = 700;
			int vc = 300;
			//empezamos con la acción
			mb.setSpeed(v0);
			mc.setSpeed(v0);
			int g=1 ;
			int r=25;
			int l=0;
			int tcont=0;
			boolean color=false;
			while(tcont==0){
				color=false;
				tcont=0;
			if(touch.isPressed() && tcont==0){
				color=true;
				tcont=1;
			while(color){
				int lector=light.getNormalizedLightValue();
				if( lector <= pa  && lector >= pb ){
					r=35;
					l=0;
					va=300;
					vc=300;
					mb.setSpeed(v0);
					mc.setSpeed(v0);
					mb.forward();
					mc.forward();
					Delay.msDelay(250);
					color=true;
				}else if((lector>=pa || lector<=pb) && g==1){
					mb.setSpeed(vc);
					mc.setSpeed(va);
					mb.forward();
					mc.backward();
					Delay.msDelay(8);
					lector=light.getNormalizedLightValue();
					if( lector <= pa && lector >= pb ){
					g=-1;}
					l++;
					}else if((lector>=pa || lector<pb) && g==-1){
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mc.forward();
						mb.backward();
						Delay.msDelay(8);
						lector=light.getNormalizedLightValue();
						if( lector <= pa && lector >= pb ){
							g=-1;}
						l++;
					
				}
				if(l>r){
					r+=25;
					g*=-1;
					l=0;
					va+=10;}
				if(touch.isPressed() && tcont==1){
					mb.backward();
					mc.backward();
					Delay.msDelay(500);						
					int t=0;
					while(t<=50){
						mc.setSpeed(vb);
						mb.setSpeed(vb);
						mc.forward();
						mb.backward();
						Delay.msDelay(8);
						t++;
						lector=light.getNormalizedLightValue();
						if( lector <= pa  && lector >= pb && t>=15 ){
							t=51;
							vc=400;}}
					tcont=2;
						}
				if(touch.isPressed() && tcont==2){
;					color=false;
						}
			}}}
		
    }
    

    public static void main (String[] args) {
        new seguidor().run();}
}
