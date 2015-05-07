import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;
import classCelda;

public class TaxiV1{
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		ColorSensor color = new ColorSensor(SensorPort.S2);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
		
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			////   La orientacion del robot sera el siguiente   ////
			////    El sentido de la calle sera el siguiente    ////
			////////////////////////////////////////////////////////
			//// Cardinalidad //// Orientacion  ////  Sentido   ////    
			////////////////////////////////////////////////////////
			////      N       ////      0      ////             ////
			////   O     E    ////   3     1   ////   0         ////
			////      S       ////      2      ////             ////
			////////////////////////////////////////////////////////
			////   Los sentidos seran los siguientes colores    ////
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
			
			////////////////////////////////////////////////////////
			////          COMENZAMOS LEYENDO EL MAPA            ////
			////////////////////////////////////////////////////////
			////  El robot cruzara toda la ciudad sin respetar  ////
			////  sentidos de las calles, haciendo primero una  ////
			////         lectura, luego, se ubicara en la       ////
			////      esquina [0][0] de nuevo y comienza el     ////
			////               <<<< MODO TAXI >>>>              ////
			////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////
			//verde=0=ooo//
			//rojo=1=oox//
			//naranjo=2=xox//
			//azul=3=xoo//
			//cafe=4=oxx//
			//amarillo=5=xxo//
			//morado=6=oxo//
			////   VALORES DE LA MATRIZ   ////
			Celda[][]calles =new Celda[4][4];
			while (matrizCalle1<4){
				while(matrizCalle2<4){
					calles[matrizCalle1][matrizCalle2].norte=TF;
					calles[matrizCalle1][matrizCalle2].sur=TF;
					calles[matrizCalle1][matrizCalle2].este=TF;
					calles[matrizCalle1][matrizCalle2].oeste=TF;
				}
			}
			int fila=1;
			int columna=1;
			int sentido=0;
			int orientacion=2;
			
			//SENSOR DE LUZ//
			int negroL=; //Indicara donde esta el pasajero, y su destino. 
			
			//SENSOR DE COLOR//
			int negroC=; //Color de la calle.        
			int verde=0//ooo//
			int rojo=1//oox//
			int naranjo=2//xox//
			int azul=3//xoo//
			int cafe=4//oxx//
			int amarillo=5//xxo//
			int morado=6//oxo//
			
			
			//>>>>>>>>>|_
			//>>  LECTURA|_
			//>>>>>>>>>>>>>|
			calles[0][0]=[[1,0],[2,-1],[3,0],[4,1]];
			//avanza hasta encontrar una esquina
			
			//aca va el codigo del seguidor
			int pa = negroC + //algo;
			int pb = negroC - //algo;
			//Velocidad del taxi
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
			boolean analisis=false;
			while(tcont==0){
				analisis=false;
				tcont=0;
				if(touch.isPressed() && tcont==0){
					analisis=true;
					tcont=1;
					while(analisis){
						int lector=color.getColorID();
						if( lector <= pa && lector >= pb ){
							r=38;
							l=0;
							g*=-1;
							va=300;
							vc=300;
							mb.setSpeed(v0);
							mc.setSpeed(v0);
							mb.forward();
							mc.forward();
							Delay.msDelay(250);
							analisis=true;
						}else if((lector>=pa || lector<=pb) && g==1){
							mb.setSpeed(vc);
							mc.setSpeed(va);
							mb.forward();
							mc.backward();
							Delay.msDelay(8);
							lector=color.getColorID();
							l++;
							}else if((lector>=pa || lector<pb) && g==-1){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.backward();
								Delay.msDelay(8);
								lector=color.getColorID();
								l++;
							}
							if(l>r){
								r+=25;
								g*=-1;
								l=0;
								va+=10;
							}
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
									lector=color.getColorID();
									if( lector <= pa && lector >= pb && t>=15 ){
										t=51;
										vc=400;
									}
								}
								tcont=2;
							}
							if(touch.isPressed() && tcont==2){
								color=false;
							}
						}
					}
			}
}
    public static void main (String[] args) {
        new TaxiV1().run();}
}
