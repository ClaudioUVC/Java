import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;
import taxi.*;

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
			int tcont=0;
			boolean analisis=false;
			boolean taxitrabajando=true;
			int posicionx=0;
			int posiciony=0;
			int mirando=3;
			boolean lleguey=false;
			boolean lleguex=false;
			boolean colors=false;
			int direccionx=3;
			int direcciony=3;
			int lector=color.getColorID();
			while(taxitrabajando==true){
				if(posicionx!=direccionx){
					lleguex=false;
				}
				if(posiciony!=direcciony){
					lleguey=false;
				}
				while(lleguex==false || lleguey==false){
				if(mirando==3 || mirando==1){
					if(direcciony>posiciony){
						lleguey=false;
						if(mirando==3){
							if(calles[posicionx][posiciony].sur==true){
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
											posiciony++;
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
										colors=false;
									}
								}
							}else if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=4;
									taxitrabajando=true;
								}
							}
						}else if(mirando==1){
							if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
								mirando=4;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
									mirando=4;
									taxitrabajando=true;
								}
							}
            }
					}else if(direcciony<posiciony){
						lleguey=false;
						if(mirando==1){
							if(calles[posicionx][posiciony].norte==true){
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
											posiciony--;
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
										colors=false;
									}
								}
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
									mirando=4;
									taxitrabajando=true;
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
								mirando=4;
								taxitrabajando=true;
							}
						}else if(mirando==3){
							if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==true){
								if(direccionx>=posicionx){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								};
									mirando=2;
									taxitrabajando=true;
								}else if(direccionx<posicionx){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=4;
									taxitrabajando=true;
								}
							}else if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=2;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].oeste==true && calles[posicionx][posiciony].este==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=4;
								taxitrabajando=true;
							}
						}
					}else if(direcciony==posiciony){
						lleguey=true;
						if(direccionx>posicionx){
							if(mirando==3){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=2;
							}else if(mirando==1){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=2;
							}
						}else if(direccionx<posicionx){
							if(mirando==3){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=4;
							}else if(mirando==1){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=4;
							}
						}else if(direccionx==posicionx){
							lleguex=true;
						}
					}
				}else if(mirando==4 || mirando==2){
					if(direccionx>posicionx){
						lleguex=false;
						if(mirando==2){
							if(calles[posicionx][posiciony].este==true){
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
											posicionx++;
											calles[posicionx][posiciony].este=colores[0][lector].sur;
											calles[posicionx][posiciony].norte=colores[0][lector].este;
											calles[posicionx][posiciony].sur=colores[0][lector].oeste;
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
										colors=false;
									}
								}
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=1;
									taxitrabajando=true;
								}
							}
						}else if(mirando==4){
							if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=1;
									taxitrabajando=true;
								}
							}
            }
					}else if(direccionx<posicionx){
						lleguex=false;
						if(mirando==4){
							if(calles[posicionx][posiciony].oeste==true){
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
											posicionx--;
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
										colors=false;
									}
								}
							}else if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=1;
									taxitrabajando=true;
								}
							}
						}else if(mirando==2){
							if(calles[posicionx][posiciony].sur==true && calles[posicionx][posiciony].norte==false){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
								taxitrabajando=true;
							}else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==true){
								if(direcciony>=posiciony){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=3;
									taxitrabajando=true;
								}else if(direcciony<posiciony){
									mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
									mirando=1;
									taxitrabajando=true;
								}
							}
						}
					
					}else if(direccionx==posicionx){
						lleguex=true;
						if(direcciony>posiciony){
							if(mirando==4){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
							}else if(mirando==2){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=1;
							}
						}else if(direcciony<posiciony){
							if(mirando==4){
								mc.setSpeed(vc);
								mb.setSpeed(va);
								mc.forward();
								mb.forward();
								Delay.msDelay(1000);
								lector=color.getColorID();
								while(lector!=black){
									//izquierda
									mc.setSpeed(vc);
									mb.setSpeed(va);
									mb.forward();
									mc.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
							}else if(mirando==2){
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
									mc.forward();
									mb.backward();
									Delay.msDelay(8);
									lector=color.getColorID();
								}
								mirando=3;
							}
						}else if(direcciony==posiciony){
							lleguey=true;
						}
					}
				}
				}
				//aqui poner script de preguntar direccion denuevo
			}
		}
	



    public static void main (String[] args) {
        new TaxiV21().run();}
}
