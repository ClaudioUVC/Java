import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;

public class seguidor2 {
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		TouchSensor touch2 = new TouchSensor(SensorPort.S2);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
			
			//LoopParaCrearColor
			int contador=0;
			int[] negro = new int[10];
			
			while(contador <= 9){
			//LimpiarPantalla
			LCD.clear();
			LCD.drawString("Light:",0,5);
			LCD.drawInt(light.getNormalizedLightValue(),7,5);
			negro[contador]=light.getNormalizedLightValue();
			contador++;
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
			//empezamos con la acción
			mb.setSpeed(v0);
			mc.setSpeed(v0);
			boolean color=true;
			int g=1 ;
			int r=25;
			int l=0;
			
			while(color){
				int lector=light.getNormalizedLightValue();
				if( lector <= pa  && lector >= pb ){
					r=30;
					l=0;
					va=300;
					mb.setSpeed(v0);
					mc.setSpeed(v0);
					mb.forward();
					mc.forward();
					Delay.msDelay(250);
					color=true;
				}else if((lector>=pa || lector<=pb) && g==1){
					mb.setSpeed(va);
					mc.setSpeed(va);
					mb.forward();
					mc.backward();
					Delay.msDelay(8);
					lector=light.getNormalizedLightValue();
					l++;
					}else if((lector>=pa || lector<pb) && g==-1){
						mc.setSpeed(va);
						mb.setSpeed(va);
						mc.forward();
						mb.backward();
						Delay.msDelay(8);
						lector=light.getNormalizedLightValue();
						l++;
					
				}
				if(l>r){
					r+=25;
					g*=-1;
					l=0;
					va+=10;}
			}
		
    }
    

    public static void main (String[] args) {
        new seguidor2().run();}
}
