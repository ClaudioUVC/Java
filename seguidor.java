import lejos.nxt.*;

public class seguidor {
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		TouchSensor touch2 = new TouchSensor(SensorPort.S1);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
		if(touch2.isPressed())
		{	
			//LoopParaCrearColor
			int contador=1;
			int [][] negro = new int[1][10];
			while(contador <= 10){

			//LimpiarPantalla
			LCD.clear();
			//Mostrar en LCD @ X=0,Y=0
			LCD.drawString("Motores B, C:",0,0);
			//Mostrar valores del motor modulo 10000 @ X=0,Y=1
			LCD.drawInt(mb.getTachoCount()%10000,6,1);
			//Mostrar valores del motor 1000 @ X=12,Y=1
			LCD.drawInt(mc.getTachoCount()%1000,12,1);

			LCD.drawString("Touch:",0,4);
			//if the sensor is pressed print X, else print O
			if(touch.isPressed()) {
				LCD.drawChar('X',7,4);
			} else {
				LCD.drawChar('O',7,4);
			}

			LCD.drawString("Light:",0,5);
			LCD.drawInt(light.getNormalizedLightValue(),7,5);
			negro[1][contador]=light.getNormalizedLightValue();
			contador++;
			}
			//valor negro final
			int promedio= (negro[1][1] + negro[1][2] + negro[1][3] + negro[1][4] + negro[1][5] + negro[1][6] + negro[1][7] + negro[1][8] + negro[1][9] + negro[1][10])/10;
			
			//CrearAncho
			int ancho=0;
			int lAncho=light.getNormalizedLightValue();
			bool cAncho=True;
			while(cAncho){
				//Leer Derecha
				if(lector>=(promedio-15) or lector<=(promedio+15)){
					rotate(1);
					cAncho=True;
					ancho++;
				}else{
					//Leer Izquierda
					rotate(-ancho);
					while(cAncho){
						if(lector>=(promedio-15) or lector<=(promedio+15)){
							rotate(-1);
							ancho++;
							cAncho=True
						}else{
							cAncho=False; 			
					}}}}
			//LimpiarPantalla
			LCD.clear();
			//Mostrar valor de negro en LCD x=0 y=0
			LCD.drawString("Negro Ancho",0,0);
			LCD.drawInt(promedio,2,1);
			LCD.drawInt(ancho,8,1);
			//Mostrar valor de Ancho
			
			//empezamos con la acción
			setSpeed(700)
			bool color=True;
			while(color){
				int lector=light.getNormalizedLightValue();
				if(lector>=(promedio-15) or lector<=(promedio+15)){
					forward();
					stop();
					color=True;
				}else{
					bool c2=true;
					int grados=0;
					while(c2=true){
						//comprobar ancho en grados de la linea**
						//Caso extremo, fin del camino.
						if(grados=180-ancho+1){
							rotate(ancho);
							lector=light.getNormalizedLightValue();
							if(lector>=(promedio-15) or lector<=(promedio+15)){
								color = false;
								c2= false;
							}}else{
							if(grados=-180+ancho+5){
								rotate(180-ancho);
								grados=0;
								while(grados<=180-ancho-5){
									rotate(1);
									lector=light.getNormalizedLightValue();
									if(lector>=(promedio-15) or lector<=(promedio+15)){
										rotate((ancho/2)-1);
										color = false;
										c2= false;
									}else{
										grados++;
										c2=true}}
							}else{
								if(grados=91){
									rotate(-90);
									grados=0;
									while(grados>=-180+ancho+5){
										rotate(-1);
										lector=light.getNormalizedLightValue();
										if(lector>=(promedio-15) or lector<=(promedio+15)){
											rotate((-ancho/2)+1);
											color = false;
											c2= false;
										}else{
											grados--;
											c2=true}}			
								}else{
									while(grados<=90){
										rotate(1);
										lector=light.getNormalizedLightValue();
										if(lector>=(promedio-15) or lector<=(promedio+15)){
											rotate((ancho/2)-1)
											color = false;
											c2 = false;
										}else{
										grados++;
										c2=true
			}}}}}}}}
		}
    }

    public static void main (String[] args) {
        new seguidor().run();
    }
}
