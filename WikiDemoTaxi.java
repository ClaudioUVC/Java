import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;

public class mirando{
	public void run(){
		//TouchSensor touch = new TouchSensor(SensorPort.S1);
		ColorHTSensor color = new ColorHTSensor(SensorPort.S4);
		//LightSensor light = new LightSensor(SensorPort.S1);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;

		////   VALORES DE LA MATRIZ CALLES   ////
		int matrizCalle1=0;
		int matrizCalle2=0;
		Celda[][]calles =new Celda[4][4];
		while (matrizCalle1<=3){
			matrizCalle2=0;
			while(matrizCalle2<=3){
				calles[matrizCalle1][matrizCalle2]=new Celda();
				calles[matrizCalle1][matrizCalle2].norte=true;
				calles[matrizCalle1][matrizCalle2].sur=true;
				calles[matrizCalle1][matrizCalle2].este=true;
				calles[matrizCalle1][matrizCalle2].oeste=true;
				matrizCalle2++;
			}
			matrizCalle1++;
		}
		//esquinas
		calles[0][0].norte=false;
		calles[0][0].este=true;
		calles[0][0].oeste=false;
		calles[0][0].sur=true;
		calles[0][3].norte=true;
		calles[0][3].este=true;
		calles[0][3].oeste=false;
		calles[0][3].sur=false;
		calles[3][0].norte=false;
		calles[3][0].este=false;
		calles[3][0].oeste=true;
		calles[3][0].sur=true;
		calles[3][3].norte=true;
		calles[3][3].este=false;
		calles[3][3].oeste=true;
		calles[3][3].sur=false;
		////   VALORES DE LA MATRIZ COLORES   ////
		Celda[][]colores =new Celda[1][15];
		int matrizColores = 0;
		while(matrizColores<=14){
			colores[0][matrizColores]=new Celda();
			colores[0][matrizColores].sur=false;
			colores[0][matrizColores].este=false;
			colores[0][matrizColores].oeste=false;
			matrizColores++;
		}
		//verde//ooo
		colores[0][1].sur=true;
		colores[0][1].este=true;
		colores[0][1].oeste=true;
		//rojo//oox
		colores[0][0].sur=true;
		colores[0][0].este=false;
		colores[0][0].oeste=true;
		//azul//xoo
		colores[0][8].sur=true;
		colores[0][8].este=true;
		colores[0][8].oeste=false;
		//amarillo//xxo
		colores[0][3].sur=true;
		colores[0][3].este=true;
		colores[0][3].oeste=false;
		//naranjo//xox
		colores[0][5].sur=true;
		colores[0][5].este=false;
		colores[0][5].oeste=false;
		//morado(magenta)//oxo
		colores[0][4].sur=true;
		colores[0][4].este=true;
		colores[0][4].oeste=true;
		//celeste(cyan)//oxx
		colores[0][6].sur=true;
		colores[0][6].este=false;
		colores[0][6].oeste=true;
		//SENSOR DE LUZ//
		int negroL=879; //Indicara donde esta el pasajero, y su destino. 
		//aca va el codigo del seguidor
		int pa = 7;
		int black=7;
		int white=2;
		int pb = 7;
		//Mostrar valor de Ancho
		int v0 = 80;
		int va = 80;
		int vb = 80;
		int vc = 80;
		//empezamos con la acción
		mb.setSpeed(v0);
		mc.setSpeed(v0);
		int g=1 ;
		int r=30;
		int l=0;
		boolean analisis=false;
		int lector=color.getColorID();
		int direccionx=1;
		int direcciony=2;
		int posicionx=0;
		int posiciony=0;
		int mirando=3;
		boolean lleguey=false;
		boolean lleguex=false;
		boolean trabajando=false;
		if(mirando==1){
			analisis=true;
			if(posiciony>direcciony){
				if(calles[posicionx][posiciony].norte==true){
					analisis=true;
					while(analisis){
						lector=color.getColorID();
						LCD.drawString("Posicion   X Y",0,0);
						LCD.drawInt(posicionx,11,1);
						LCD.drawInt(posiciony,13,1);
						LCD.drawString("Direccion  X Y",0,3);
						LCD.drawInt(direccionx,11,4);
						LCD.drawInt(direcciony,13,4);
						LCD.drawInt(lector,13,5);
						LCD.drawInt(mirando,13,6);
						if(lector==black){
							r=38;
							l=0;
							g*=-1;
							va=100;
							vc=100;
							mb.setSpeed(v0);
							mc.setSpeed(v0);
							mb.forward();
							mc.forward();
							Delay.msDelay(250);
							lector=color.getColorID();
							if(lector!=black && lector!=white){
								mb.stop();
								mc.stop();
								Delay.msDelay(2000);
								lector=color.getColorID();
								posiciony--;
								LCD.drawInt(posicionx,11,1);
								LCD.drawInt(posiciony,13,1);
								LCD.drawInt(mirando,13,6);
								calles[posicionx][posiciony].norte=colores[0][lector].sur;
								calles[posicionx][posiciony].este=colores[0][lector].oeste;
								calles[posicionx][posiciony].oeste=colores[0][lector].este;
								calles[0][0].norte=false;
								calles[0][0].este=true;
								calles[0][0].oeste=false;
								calles[0][0].sur=true;
								calles[0][3].norte=true;
								calles[0][3].este=true;
								calles[0][3].oeste=false;
								calles[0][3].sur=false;
								calles[3][0].norte=false;
								calles[3][0].este=false;
								calles[3][0].oeste=true;
								calles[3][0].sur=true;
								calles[3][3].norte=true;
								calles[3][3].este=false;
								calles[3][3].oeste=true;
								calles[3][3].sur=false;
								mb.forward();
								mc.forward();
								Delay.msDelay(200);
								trabajando=true;
								break;
								}
							}else if(lector==white && g==1){
								mb.setSpeed(vc);
								mc.setSpeed(va);
								mb.forward();
								mc.backward();
								Delay.msDelay(8);
								lector=color.getColorID();
								l++;
							}else if(lector==white && g==-1){
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
					}			
				}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==true){
					if(posicionx<=direccionx){
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mc.forward();
						mb.forward();
						Delay.msDelay(1000);
						lector=color.getColorID();
						while(lector!=black){
							//izquierda
							mb.setSpeed(vc);
							mc.setSpeed(va);
							mc.forward();
							mb.backward();
							Delay.msDelay(8);
							lector=color.getColorID();
						}
						mirando=mirando-1;
						LCD.drawInt(mirando,13,6);
						trabajando=true;
					}else if(posicionx<direccionx){
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mc.forward();
						mb.forward();
						Delay.msDelay(1000);
						lector=color.getColorID();
						while(lector!=black){
							mc.setSpeed(vc);
							mb.setSpeed(va);
							mb.forward();
							mc.backward();
							Delay.msDelay(8);
							lector=color.getColorID();
						}
						mirando=mirando+1;
						LCD.drawInt(mirando,13,6);
						trabajando=true;
					}
				}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
					mc.setSpeed(vc);
					mb.setSpeed(va);
					mc.forward();
					mb.forward();
					Delay.msDelay(1000);
					lector=color.getColorID();
					while(lector!=black){
						//derecha
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mb.forward();
						mc.backward();
						Delay.msDelay(8);
						lector=color.getColorID();
					}
					mirando=mirando+1;
					LCD.drawInt(mirando,13,6);
					trabajando=true;
				}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].oeste==true){
					mc.setSpeed(vc);
					mb.setSpeed(va);
					mc.forward();
					mb.forward();
					Delay.msDelay(1000);
					lector=color.getColorID();
					while(lector!=black){
						//izquierda
						mb.setSpeed(vc);
						mc.setSpeed(va);
						mc.forward();
						mb.backward();
						Delay.msDelay(8);
						lector=color.getColorID();
					}
					mirando=mirando-1;
					LCD.drawInt(mirando,13,6);
					trabajando=true;
				}
			}else if(posiciony>=direcciony){
				if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==true){
					if(posicionx>=direccionx){
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mc.forward();
						mb.forward();
						Delay.msDelay(1000);
						lector=color.getColorID();
						while(lector!=black){
							//izquierda
							mb.setSpeed(vc);
							mc.setSpeed(va);
							mc.forward();
							mb.backward();
							Delay.msDelay(8);
							lector=color.getColorID();
						}
						mirando=mirando-1;
						LCD.drawInt(mirando,13,6);
						trabajando=true;
					}else if(posicionx<direccionx){
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mc.forward();
						mb.forward();
						Delay.msDelay(1000);
						lector=color.getColorID();				
						while(lector!=black){
							//derecha
							mc.setSpeed(vc);
							mb.setSpeed(va);
							mb.forward();
							mc.backward();
							Delay.msDelay(8);
							lector=color.getColorID();
						}
						mirando=mirando+1;
						LCD.drawInt(mirando,13,6);
						trabajando=true;
					}
				}else if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
					mc.setSpeed(vc);
					mb.setSpeed(va);
					mc.forward();
					mb.forward();
					Delay.msDelay(1000);
					lector=color.getColorID();
					while(lector!=black){
						//derecha
						mc.setSpeed(vc);
						mb.setSpeed(va);
						mb.forward();
						mc.backward();
						Delay.msDelay(8);
						lector=color.getColorID();
					}
					mirando=mirando+1;
					LCD.drawInt(mirando,13,6);
					trabajando=true;
				}else if(calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].oeste==true){
					mc.setSpeed(vc);
					mb.setSpeed(va);
					mc.forward();
					mb.forward();
					Delay.msDelay(1000);
					lector=color.getColorID();
					while(lector!=black){
						//izquierda
						mb.setSpeed(vc);
						mc.setSpeed(va);
						mc.forward();
						mb.backward();
						Delay.msDelay(8);
						lector=color.getColorID();
					}
					mirando=mirando-1;
					LCD.drawInt(mirando,13,6);
					trabajando=true;
				}
			}
		}
	}
    public static void main (String[] args) {
        new mirando().run();}
}
