import lejos.nxt.*;

public class seguidor {
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		TouchSensor touch2 = new TouchSensor(SensorPort.S2);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
		if(touch2.isPressed()){	
			//LoopParaCrearColor
			int contador=0;
			int[][] negro = new int[1][10];
			
			while(contador <= 9){
			//LimpiarPantalla
			LCD.clear();
			LCD.drawString("Light:",0,5);
			LCD.drawInt(light.getNormalizedLightValue(),7,5);
			negro[1][contador]=light.getNormalizedLightValue();
			contador++;
			}
			//valor negro final
			int promedio = (negro[1][0] + negro[1][1] + negro[1][2] + negro[1][3] + negro[1][4] + negro[1][5] + negro[1][6] + negro[1][7] + negro[1][8] + negro[1][9])/10;
			int pa = promedio + 15;
			int pb = promedio - 15;
			//LimpiarPantalla
			LCD.clear();
			//Mostrar valor de negro en LCD x=0 y=0
			LCD.drawString("Negro",0,0);
			LCD.drawInt(promedio,2,1);
			//Mostrar valor de Ancho
			int v0 = 300;
			int va = 500;
			//empezamos con la acción
			mb.setSpeed(v0);
			mc.setSpeed(v0);
			boolean color=true;
			while(color){
				int lector=light.getNormalizedLightValue();
				if( lector >= pa  && lector <= pb ){
					mb.setSpeed(v0);
					mc.setSpeed(v0);
					mb.forward();
					mc.forward();
					color=true;
				}else{
					mb.setSpeed(va);
					mc.setSpeed(v0);
					mb.forward();
					mc.forward();
					Delay.msDelay(10)
					int lector=light.getNormalizedLightValue();
					if( lector >= pa  && lector <= pb ){
						color=true;
					}else{
						mc.setSpeed(va);
						mb.setSpeed(v0);
						mc.forward();
						mb.forward();
						Delay.msDelay(10)
						int lector=light.getNormalizedLightValue();
						if( lector >= pa  && lector <= pb ){
							color=true;
						}else{
							if(touch2.isPressed()){
					//cuando choca y se devuelve}
							} 
						}
					}
				}
			}
		}
    }
    

    public static void main (String[] args) {
        new seguidor().run();}
}
