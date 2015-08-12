import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;
import java.util.Random;

public class Taxi{
	public void run(){
		//TouchSensor touch = new TouchSensor(SensorPort.S1);
		ColorHTSensor color = new ColorHTSensor(SensorPort.S4);
		LightSensor light = new LightSensor(SensorPort.S1);		
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
		colores[0][6].sur=false;
		colores[0][6].este=false;
		colores[0][6].oeste=true;
		//SENSOR DE LUZ//
		int negroluz1=325;
		int negroluz2=375;//Indicara donde esta el pasajero, y su destino. 
		//aca va el codigo del seguidor
		int pa = 7;
		int black=7;
		int white=2;
		int pb = 7;
		//Mostrar valor de Ancho
		int v0 = 125;
		int va = 125;
		int vb = 125;
		int vc = 125;
		//empezamos con la acción
		mb.setSpeed(v0);
		mc.setSpeed(v0);
		int g=1 ;
		int r=30;
		int l=0;
		boolean analisis=false;
		int lector=color.getColorID();
		int lectorpasajero=light.getNormalizedLightValue();
		int direccionx=0;
		int direcciony=0;
		int posicionx=0;
		int posiciony=0;
		int mirando=3;
		boolean lleguey=false;
		boolean lleguex=false;
		boolean trabajando=false;
		boolean pasajero = false;
		boolean buscarpasajero = false;
		while(trabajando==false){
			if(pasajero=false){
				int pregunta=1;
				while(direccionx==posicionx && direcciony==posiciony){
					LCD.drawString("Desea trabajar" , 0 , 0);
					LCD.drawString("o terminar?" , 0 , 1);					
					if(Button.LEFT.isDown()){pregunta=pregunta*(-1);LCD.drawString("terminar" , 0 , 3);Delay.msDelay(500);}
					if(Button.RIGHT.isDown()){pregunta=pregunta*(-1);LCD.drawString("trabajar" , 0 , 3);Delay.msDelay(500);}
					if(Button.ENTER.isDown()){
						if(pregunta==1){
							buscarpasajero=true;
						}else if(pregunta==-1){
							break;
						}
						Delay.msDelay(500);
						break;
					}
				}
				if(buscarpasajero==true){
					Random generator = new Random();
					direccionx = generator.nextInt(4);
					direcciony = generator.nextInt(4);
				}								
			}
			while(direccionx!=posicionx || direcciony!=posiciony){
				trabajando=true;
				while(trabajando){
					if(mirando==5){
						mirando=1;
						if(direccionx==posicionx && direcciony==posiciony){
							trabajando=false;
						}
						trabajando=true;
					}else if(mirando==0){
						mirando=4;
					}					
					LCD.clear();
					LCD.drawString("Posicion   X Y",0,0);
					LCD.drawInt(posicionx,11,1);
					LCD.drawInt(posiciony,13,1);
					LCD.drawString("Direccion  X Y",0,3);
					LCD.drawInt(direccionx,11,4);
					LCD.drawInt(direcciony,13,4);
					LCD.drawInt(lector,13,4);
					//Mostrar valor de Ancho					
					if(mirando==1){
						if(calles[posicionx][posiciony].norte==true){
							analisis=true;
							while(analisis){								
								lector=color.getColorID();
								LCD.clear();
								LCD.drawString("Posicion   X Y",0,0);
								LCD.drawInt(posicionx,11,1);
								LCD.drawInt(posiciony,13,1);
								LCD.drawString("Direccion  X Y",0,3);
								LCD.drawInt(direccionx,11,4);
								LCD.drawInt(direcciony,13,4);
								LCD.drawInt(lector,13,4);
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
									if(pasajero=false){
										lectorpasajero=light.getNormalizedLightValue();
										if(lectorpasajero>=negroluz1 && lectorpasajero<=negroluz2){
											pasajero=true;
											int i = 0;
											while(true){
											LCD.clear();
											LCD.drawString("que direccion" , 0 , 0);
											LCD.drawString("quiere ir x?" , 0 , 1);
											LCD.drawString(Integer.toString(i), 0 , 2);
											if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
											if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
											if(Button.ENTER.isDown()){direccionx = i;Delay.msDelay(500); break;}	
											}
											while(true){
												LCD.clear();
												LCD.drawString("que direccion" , 0 , 0);
												LCD.drawString("quiere ir y?" , 0 , 1);
												LCD.drawString(Integer.toString(i), 0 , 2);
												if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
												if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
												if(Button.ENTER.isDown()){direcciony = i;Delay.msDelay(500); break;}	
											}
										}
									}
									if(lector!=black && lector!=white){
										mb.stop();
										mc.stop();
										Delay.msDelay(1000);
										lector=color.getColorID();
										posiciony--;
										LCD.drawInt(posicionx,11,1);
										LCD.drawInt(posiciony,13,1);
										calles[posicionx][posiciony].norte=colores[0][lector].sur;
										calles[posicionx][posiciony].este=colores[0][lector].oeste;
										calles[posicionx][posiciony].oeste=colores[0][lector].este;
										if(direccionx==posicionx && direcciony==posiciony){
											trabajando=false;
										}
										trabajando=true;
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
						}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==true){
							if(posicionx>=direccionx){
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mb.setSpeed(vdf);
								mc.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///
								mirando=mirando-1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
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
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}
						}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
							//Mostrar valor de Ancho
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mc.setSpeed(vdf);
							mb.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///      
							mirando=mirando+1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}else if(calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].oeste==true){
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mb.setSpeed(vdf);
							mc.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///
							mirando=mirando-1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}
					
					}else if(mirando==2){
						if(calles[posicionx][posiciony].este==true){
							analisis=true;
							while(analisis){
								lector=color.getColorID();
								LCD.clear();
								LCD.drawString("Posicion   X Y",0,0);
								LCD.drawInt(posicionx,11,1);
								LCD.drawInt(posiciony,13,1);
								LCD.drawString("Direccion  X Y",0,3);
								LCD.drawInt(direccionx,11,4);
								LCD.drawInt(direcciony,13,4);
								LCD.drawInt(lector,13,4);
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
									if(pasajero=false){
										lectorpasajero=light.getNormalizedLightValue();
										if(lectorpasajero>=negroluz1 && lectorpasajero<=negroluz2){
											pasajero=true;
											int i = 0;
											while(true){
											LCD.clear();
											LCD.drawString("que direccion" , 0 , 0);
											LCD.drawString("quiere ir x?" , 0 , 1);
											LCD.drawString(Integer.toString(i), 0 , 2);
											if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
											if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
											if(Button.ENTER.isDown()){direccionx = i;Delay.msDelay(500); break;}	
											}
											while(true){
												LCD.clear();
												LCD.drawString("que direccion" , 0 , 0);
												LCD.drawString("quiere ir y?" , 0 , 1);
												LCD.drawString(Integer.toString(i), 0 , 2);
												if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
												if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
												if(Button.ENTER.isDown()){direcciony = i;Delay.msDelay(500); break;}	
											}
										}
									}
									if(lector!=black && lector!=white){
										mb.stop();
										mc.stop();
										Delay.msDelay(1000);
										lector=color.getColorID();
										posicionx++;
										LCD.drawInt(posicionx,11,1);
										LCD.drawInt(posiciony,13,1);
										calles[posicionx][posiciony].este=colores[0][lector].sur;
										calles[posicionx][posiciony].sur=colores[0][lector].oeste;
										calles[posicionx][posiciony].norte=colores[0][lector].este;
										if(direccionx==posicionx && direcciony==posiciony){
											trabajando=false;
										}
										trabajando=true;
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
						}else if(calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==true){
							if(posiciony>=direcciony){
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mb.setSpeed(vdf);
								mc.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///
								mirando=mirando-1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}else if(posiciony<direcciony){
								//Mostrar valor de Ancho
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mc.setSpeed(vdf);
								mb.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///       
								mirando=mirando+1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}
						}else if(calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
							//Mostrar valor de Ancho
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mc.setSpeed(vdf);
							mb.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///        
							mirando=mirando+1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}else if(calles[posicionx][posiciony].este==false && calles[posicionx][posiciony].sur==false && calles[posicionx][posiciony].norte==true){
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mb.setSpeed(vdf);
							mc.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///
							mirando=mirando-1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}
					}else if(mirando==3){
						LCD.clear();
						LCD.drawString("Posicion   X Y",0,0);
						LCD.drawInt(posicionx,11,1);
						LCD.drawInt(posiciony,13,1);
						LCD.drawString("Direccion  X Y",0,3);
						LCD.drawInt(direccionx,11,4);
						LCD.drawInt(direcciony,13,4);
						LCD.drawInt(lector,13,5);
						if(calles[posicionx][posiciony].sur==true){
							analisis=true;
							while(analisis){
								lector=color.getColorID();
								LCD.drawInt(lector,13,5);
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
									if(pasajero=false){
										lectorpasajero=light.getNormalizedLightValue();
										if(lectorpasajero>=negroluz1 && lectorpasajero<=negroluz2){
											pasajero=true;
											int i = 0;
											while(true){
											LCD.clear();
											LCD.drawString("que direccion" , 0 , 0);
											LCD.drawString("quiere ir x?" , 0 , 1);
											LCD.drawString(Integer.toString(i), 0 , 2);
											if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
											if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
											if(Button.ENTER.isDown()){direccionx = i;Delay.msDelay(500); break;}	
											}
											while(true){
												LCD.clear();
												LCD.drawString("que direccion" , 0 , 0);
												LCD.drawString("quiere ir y?" , 0 , 1);
												LCD.drawString(Integer.toString(i), 0 , 2);
												if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
												if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
												if(Button.ENTER.isDown()){direcciony = i;Delay.msDelay(500); break;}	
											}
										}
									}
									LCD.drawInt(lector,13,5);
									if(lector!=black && lector!=white){
										mb.stop();
										mc.stop();
										Delay.msDelay(1000);
										lector=color.getColorID();
										LCD.drawInt(lector,13,5);
										posiciony++;
										LCD.drawInt(posicionx,11,1);
										LCD.drawInt(posiciony,13,1);
										calles[posicionx][posiciony].sur=colores[0][lector].sur;
										calles[posicionx][posiciony].oeste=colores[0][lector].oeste;
										calles[posicionx][posiciony].este=colores[0][lector].este;
										if(direccionx==posicionx && direcciony==posiciony){
											trabajando=false;
										}
										trabajando=true;
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
						}else if(calles[posicionx][posiciony].sur==false && calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
							if(posicionx<direccionx){
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mb.setSpeed(vdf);
								mc.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///
								mirando=mirando-1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}else if(posicionx>=direccionx){
								//Mostrar valor de Ancho
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mc.setSpeed(vdf);
								mb.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///    
								mirando=mirando+1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}
						}else if(calles[posicionx][posiciony].sur==false && calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
							//Mostrar valor de Ancho
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mc.setSpeed(vdf);
							mb.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///      
							mirando=mirando+1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}else if(calles[posicionx][posiciony].sur==false && calles[posicionx][posiciony].oeste==false && calles[posicionx][posiciony].este==true){
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mb.setSpeed(vdf);
							mc.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///
							mirando=mirando-1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}
					}else if(mirando==4){
						LCD.clear();
						LCD.drawString("Posicion   X Y",0,0);
						LCD.drawInt(posicionx,11,1);
						LCD.drawInt(posiciony,13,1);
						LCD.drawString("Direccion  X Y",0,3);
						LCD.drawInt(direccionx,11,4);
						LCD.drawInt(direcciony,13,4);						
						if(calles[posicionx][posiciony].oeste==true){
							analisis=true;
							while(analisis){
								lector=color.getColorID();
								LCD.drawInt(lector,13,5);
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
									if(pasajero=false){
										lectorpasajero=light.getNormalizedLightValue();
										if(lectorpasajero>=negroluz1 && lectorpasajero<=negroluz2){
											pasajero=true;
											int i = 0;
											while(true){
											LCD.clear();
											LCD.drawString("que direccion" , 0 , 0);
											LCD.drawString("quiere ir x?" , 0 , 1);
											LCD.drawString(Integer.toString(i), 0 , 2);
											if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
											if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
											if(Button.ENTER.isDown()){direccionx = i;Delay.msDelay(500); break;}	
											}
											while(true){
												LCD.clear();
												LCD.drawString("que direccion" , 0 , 0);
												LCD.drawString("quiere ir y?" , 0 , 1);
												LCD.drawString(Integer.toString(i), 0 , 2);
												if(Button.LEFT.isDown() && i > 0){i--;Delay.msDelay(500);}
												if(Button.RIGHT.isDown() && i < 3){i++;Delay.msDelay(500);}
												if(Button.ENTER.isDown()){direcciony = i;Delay.msDelay(500); break;}	
											}
										}
									}
									LCD.drawInt(lector,13,5);
									if(lector!=black && lector!=white){
										mb.stop();
										mc.stop();
										Delay.msDelay(1000);
										lector=color.getColorID();
										LCD.drawInt(lector,13,5);
										posicionx--;
										LCD.drawInt(posicionx,11,1);
										LCD.drawInt(posiciony,13,1);
										calles[posicionx][posiciony].oeste=colores[0][lector].sur;
										calles[posicionx][posiciony].norte=colores[0][lector].oeste;
										calles[posicionx][posiciony].sur=colores[0][lector].este;
										if(direccionx==posicionx && direcciony==posiciony){
											trabajando=false;
										}
										trabajando=true;
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
						}else if(calles[posicionx][posiciony].oeste==false && calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
							if(posiciony<direcciony){
								int va = 125;
								int vdf = 500;
								int vdd = 50;
								//empezamos con la acción
								mb.setSpeed(va);
								mc.setSpeed(va);
								int g=1 ;
								int r=30;
								int l=0;
								int tcont=0;
								///DERECHA
								va=125;
								mb.setSpeed(va);
								mc.setSpeed(va);
								mb.forward();
								mc.forward();
								Delay.msDelay(2000);
								mb.setSpeed(vdf);
								mc.setSpeed(vdd);
								mb.forward();
								mc.forward();
								Delay.msDelay(700);
								///
								mirando=mirando-1;
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}else if(posiciony>=direcciony){
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
								if(direccionx==posicionx && direcciony==posiciony){
									trabajando=false;
								}
								trabajando=true;
							}
						}else if(calles[posicionx][posiciony].oeste==false && calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
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
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}else if(calles[posicionx][posiciony].oeste==false && calles[posicionx][posiciony].norte==false && calles[posicionx][posiciony].sur==true){
							int va = 125;
							int vdf = 500;
							int vdd = 50;
							//empezamos con la acción
							mb.setSpeed(va);
							mc.setSpeed(va);
							int g=1 ;
							int r=30;
							int l=0;
							int tcont=0;
							///DERECHA
							va=125;
							mb.setSpeed(va);
							mc.setSpeed(va);
							mb.forward();
							mc.forward();
							Delay.msDelay(2000);
							mb.setSpeed(vdf);
							mc.setSpeed(vdd);
							mb.forward();
							mc.forward();
							Delay.msDelay(700);
							///
							mirando=mirando-1;
							if(direccionx==posicionx && direcciony==posiciony){
								trabajando=false;
							}
							trabajando=true;
						}
					}
				}
			}
		}
	}
    public static void main (String[] args) {
        new Taxi().run();}
}
