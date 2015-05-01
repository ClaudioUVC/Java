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
			int orientacion=0;
			int[][] calles = new int[4][4];

			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			////   La orientacion del robot sera el siguiente   ////
			////    El sentido de la calle sera el siguiente    ////
			////////////////////////////////////////////////////////
			//// Cardinalidad //// Orientacion  ////  Sentido   ////    
			////////////////////////////////////////////////////////
			////      N       ////      1      ////     -1      ////
			////   O     E    ////   4     3   ////  -1  0  1   ////
			////      S       ////      2      ////      1      ////
			////////////////////////////////////////////////////////
			//// Los sentidos seran con los siguientes colores  ////
			////////////////////////////////////////////////////////
			////    Verde=0   ///    Rojo=-1   ////   Azul=1    ////
			////////////////////////////////////////////////////////
			////     El robot comienza en la esquina [0][0]     ////
			////   El robot comienza mirando hacia el sur (2)   ////
			////    Esta calle comienza con sentido nulo (0)    ////
			////////////////////////////////////////////////////////
			////               MAPA DE LA CIUDAD                ////
			////////////////////////////////////////////////////////
			//// [0][0]=======[1][0]=======[2][0]=======[3][0]  ////
			////   ||           ||           ||           ||    ////
			//// [0][1]=======[1][1]=======[2][1]=======[3][1]  ////
			////   ||           ||           ||           ||    ////
			//// [0][2]=======[1][2]=======[2][2]=======[3][2]  ////
			////   ||           ||           ||           ||    ////
			//// [0][3]=======[1][3]=======[2][3]=======[3][3]  ////
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			
			while(columna <= 3)
			{
				//este while trabajara solo con orientaciones 1-2  
				sentido=0;
				while(fila<=3)
				{
					//este while trabajara solo con orientaciones 3-4
					if (color.getnormalizedColorValue()==rojo){
						sentido=-1;
					}else if(color.getnormalizedColorValue()==azul){
						sentido=1;
					}else if(color.getnormalizedColorValue==verde){
						sentido=0
					}
				calles[fila][columna]={sentido,;
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
