import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;
import classCelda;

public class TaxiV2{
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
			////   VALORES DE LA MATRIZ CALLES   ////
			matrizCalle1=1;
			matrizCalle2=1;
			Celda[][]calles =new Celda[4][4];
			while (matrizCalle1<4){
				while(matrizCalle2<4){
					calles[matrizCalle1][matrizCalle2].norte=null;
					calles[matrizCalle1][matrizCalle2].sur=null;
					calles[matrizCalle1][matrizCalle2].este=null;
					calles[matrizCalle1][matrizCalle2].oeste=null;
					matrizCalle2=+1;
				}
				matrizCalle1=+1;
			}
			////   VALORES DE LA MATRIZ COLORES   ////
			Celda[][]colores =new Celda[1][12];
			//verde//ooo
			calles[0][1].sur=true;
			calles[0][1].este=true;
			calles[0][1].oeste=true;
			//rojo//oox
			calles[0][0].sur=true;
			calles[0][0].este=false;
			calles[0][0].oeste=true;
			//azul//xoo
			calles[0][2].sur=true;
			calles[0][2].este=true;
			calles[0][2].oeste=false;
			//amarillo//xxo
			calles[0][3].sur=false;
			calles[0][3].este=true;
			calles[0][3].oeste=false;
			//naranjo//xox
			calles[0][5].sur=true;
			calles[0][5].este=false;
			calles[0][5].oeste=false;
			//morado(magenta)//oxo
			calles[0][4].sur=false;
			calles[0][4].este=true;
			calles[0][4].oeste=true;
			//celeste(cyan)//oxx
			calles[0][7].sur=false;
			calles[0][7].este=false;
			calles[0][7].oeste=true;
			//SENSOR DE LUZ//
			int negroL=; //Indicara donde esta el pasajero, y su destino. 
			//aca va el codigo del seguidor
			int pa = 7; //algo;
			int pb = 7; //algo;
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
			taxitrabajando=true;
			posicionx=0;
			posiciony=0;
			mirando=3;
			while(taxitrabajando==true){
				if(mirando==3 || mirando==1){
					if(direcciony>posiciony){
						if(mirando==3){
							if(calles[posicionx][posiciony].sur==true){
								analisis==true;
								while(analisis){
									int lector=color.getColorID();
									if(lector==black){
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
										lector=color.getColorID();
										if(lector=!black || lector=!white){
											mb.stop();
											mc.stop();
											Delay.msDelay(1000);
											lector=color.getColorID();
											posiciony+1;
											calles[posicionx][posiciony].sur=colores[0][lector].sur;
											calles[posicionx][posiciony].este=colores[0][lector].este;
											calles[posicionx][posiciony].oeste=colores[0][lector].oeste;
											taxitrabajando=true;
										}else{
												analisis=true;
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
							}else if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								doblar izquierda
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								doblar derecha
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									doblar izquierda
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									doblar derecha
									mirando=4;
									taxitrabajando=true;
								}
							}
						}else if(mirando==1){
							if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								doblar derecha
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								doblar izquierda
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									doblar derecha
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									doblar izquierda
									mirando=4;
									taxitrabajando=true;
								}
							}
            }
					}else if(direcciony<posiciony){
						if(mirando==1){
							if(calles[posicionx][posiciony].norte==true){
								analisis==true;
								while(analisis){
									int lector=color.getColorID();
									if(lector==black){
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
										lector=color.getColorID();
										if(lector=!black || lector=!white){
											mb.stop();
											mc.stop();
											Delay.msDelay(1000);
											lector=color.getColorID();
											posiciony-1;
											calles[posicionx][posiciony].norte=colores[0][lector].sur;
											calles[posicionx][posiciony].oeste=colores[0][lector].este;
											calles[posicionx][posiciony].este=colores[0][lector].oeste;
											taxitrabajando=true;
										}else{
											analisis=true;
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
							}else if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								doblar derecha
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								doblar izquierda
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									doblar derecha
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									doblar izquierda
									mirando=4;
									taxitrabajando=true;
								}
							}
						}else if(mirando==3){
							if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								doblar izquierda
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								doblar derecha
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									doblar izquierda
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									doblar derecha
									mirando=4;
									taxitrabajando=true;
								}
							}
						}
					}
				}else if(mirando==4 || mirando==2){
					if(direccionx>posicionx){
						if(mirando==2){
							if(calles[posicionx][posiciony].este==true){
								analisis==true;
								while(analisis){
									int lector=color.getColorID();
									if(lector==black){
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
										lector=color.getColorID();
										if(lector=!black || lector=!white){
											mb.stop();
											mc.stop();
											Delay.msDelay(1000);
											lector=color.getColorID();
											posicionx+1;
											calles[posicionx][posiciony].este=colores[0][color].sur;
											calles[posicionx][posiciony].norte=colores[0][color].este;
											calles[posicionx][posiciony].sur=colores[0][color].oeste;
											taxitrabajando=true;
										}else{
											analisis=true;
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
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								doblar izquierda
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								doblar derecha
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									doblar derecha
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									doblar izquierda
									mirando=1;
									taxitrabajando=true;
								}
							}
						}else if(mirando==4){
							if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								doblar derecha
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								doblar izquierda
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									doblar izquierda
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									doblar derecha
									mirando=1;
									taxitrabajando=true;
								}
							}
            }
					}else if(direccionx<posicionx){
						if(mirando==4){
							if(calles[posicionx][posiciony].oeste==true){
								analisis==true;
								while(analisis){
									int lector=color.getColorID();
									if(lector==black){
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
										lector=color.getColorID();
										if(lector=!black || lector=!white){
											mb.stop();
											mc.stop();
											Delay.msDelay(1000);
											lector=color.getColorID();
											posicionx-1;
											calles[posicionx][posiciony].oeste=colores[0][lector].sur;
											calles[posicionx][posiciony].sur=colores[0][lector].este;
											calles[posicionx][posiciony].norte=colores[0][lector].oeste;
											taxitrabajando=true;
										}else{
											analisis=true;
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
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								doblar izquierda
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								doblar derecha
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									doblar izquierda
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									doblar derecha
									mirando=1;
									taxitrabajando=true;
								}
							}
						}else if(mirando==2){
							if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								doblar derecha
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								doblar izquierda
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									doblar derecha
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									doblar izquierda
									mirando=1;
									taxitrabajando=true;
								}
							}
						}
					}
				}
			}
		}
	}
}


}
    public static void main (String[] args) {
        new TaxiV2().run();}
}
