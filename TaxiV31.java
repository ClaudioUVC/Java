import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;

public class TaxiV21{
    public void run(){
    		TouchSensor touch = new TouchSensor(SensorPort.S1);
		ColorHTSensor color = new ColorHTSensor(SensorPort.S4);
		LightSensor light = new LightSensor(SensorPort.S3);		
		NXTRegulatedMotor mb = Motor.B;
		NXTRegulatedMotor mc = Motor.C;
		
			////   VALORES DE LA MATRIZ CALLES   ////
			int matrizCalle1=0;
			int matrizCalle2=0;
			Celda[][]calles =new Celda[4][4];
			while (matrizCalle1<4){
				while(matrizCalle2<4){
					calles[matrizCalle1][matrizCalle2].norte=true;
					calles[matrizCalle1][matrizCalle2].sur=true;
					calles[matrizCalle1][matrizCalle2].este=true;
					calles[matrizCalle1][matrizCalle2].oeste=true;
					matrizCalle2++;
				}
				matrizCalle1++;
			}
			////   VALORES DE LA MATRIZ COLORES   ////
			Celda[][]colores =new Celda[1][12];
			//verde//ooo
			colores[0][1].sur=true;
			colores[0][1].este=true;
			colores[0][1].oeste=true;
			//rojo//oox
			colores[0][0].sur=true;
			colores[0][0].este=false;
			colores[0][0].oeste=true;
			//azul//xoo
			colores[0][2].sur=true;
			colores[0][2].este=true;
			colores[0][2].oeste=false;
			//amarillo//xxo
			colores[0][3].sur=false;
			colores[0][3].este=true;
			colores[0][3].oeste=false;
			//naranjo//xox
			colores[0][5].sur=true;
			colores[0][5].este=false;
			colores[0][5].oeste=false;
			//morado(magenta)//oxo
			colores[0][4].sur=false;
			colores[0][4].este=true;
			colores[0][4].oeste=true;
			//celeste(cyan)//oxx
			colores[0][7].sur=false;
			colores[0][7].este=false;
			colores[0][7].oeste=true;
			//SENSOR DE LUZ//
			int negroL=879; //Indicara donde esta el pasajero, y su destino. 
			//aca va el codigo del seguidor
			int pa = 7; //algo;
			int pb = 7; //algo;
			//Velocidad del taxi
			int v0 = 350;
			int va = 300;
			int vb = 700;
			int vc = 300;
			int white=2;
			int black=7;
			//empezamos con la acción
			mb.setSpeed(v0);
			mc.setSpeed(v0);
			int g=1 ;
			int r=25;
			int l=0;
			boolean analisis=false;
			int lector=color.getColorID();
			int direccionx=3;
			int direcciony=3;
			int posicionx=0;
			int posiciony=0;
			int mirando=3;
			boolean lleguey=false;
			boolean lleguex=false;
			boolean trabajando=false;
			char pos="sur";
			char pos1="sur";
			char pos2="sur";
			char pos3="sur";
			if(trabajando==false){
				//preguntar direccion.
				while(direccionx!=posicionx || direcciony!=posiciony){
					trabajando=true;
					while(trabajando){
						if(mirando==1){
							pos="norte";
							pos1="este";
							pos2="sur";    
							pos3="oeste";
						}else if(mirando==2){
							pos="este";
							pos1="sur";
							pos2="oeste";
							pos3="norte";
						}else if(mirando==3){
							pos="sur";
							pos1="oeste";
							pos2="norte";
							pos3="este";
						}else if(mirando==4){
							pos="oeste";
							pos1="norte";
							pos2="este";
							pos3="sur";
						}
						if(calles[posicionx][posiciony].pos==true){
							analisis=true;
							while(analisis){
								lector=color.getColorID();
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
									if(lector!=black || lector!=white){
										mb.stop();
										mc.stop();
										Delay.msDelay(1000);
										lector=color.getColorID();
										if(mirando==1){
											posiciony--;
										}else if(mirando==2){
											posicionx++;
										}else if(mirando==3){
											posiciony++;
										}else if(mirando==4){
											posicionx--;
										}
										calles[posicionx][posiciony].pos=colores[0][lector].sur;
										calles[posicionx][posiciony].pos1=colores[0][lector].oeste;
										calles[posicionx][posiciony].pos3=colores[0][lector].este;
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
							}
						}else if(calles[posicionx][posiciony].pos==false && calles[posicionx][posiciony].pos1==true && calles[posicionx][posiciony].pos3==true){
							if(mirando==1){
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
									trabajando=true;
								}
							}else if(mirando==2){
								if(posiciony>=direcciony){
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
									trabajando=true;
								}else if(posiciony<direcciony){
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
									trabajando=true;
								}
							}else if(mirando==3){
								if(posicionx>=direccionx){
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
									trabajando=true;
								}else if(posicionx<direccionx){
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
									trabajando=true;
								}
							}else if(mirando==4){
								if(posiciony>=direcciony){
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
									trabajando=true;
								}else if(posiciony<direcciony){
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
									trabajando=true;
								}
							}
						}else if(calles[posicionx][posiciony].pos==false && calles[posicionx][posiciony].pos1==true && calles[posicionx][posiciony].pos3==false){
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
							trabajando=true;
						}else if(calles[posicionx][posiciony].pos==false && calles[posicionx][posiciony].pos1==false && calles[posicionx][posiciony].pos3==true){
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
							trabajando=true;
						}
					}
				}
			}
		}
    public static void main (String[] args) {
        new TaxiV21().run();}
}
