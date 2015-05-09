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
            }}
          if(direcciony<posiciony){
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
                  taxitrabajando=true;}
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
        scriptseguidor
        lector=blah
        if(lector=!negroC){
          posicionx+1;
          analizar color
          calles[posicionx][posiciony].este=colores[0][color].sur;
          calles[posicionx][posiciony].norte=colores[0][color].este;
          calles[posicionx][posiciony].sur=colores[0][color].oeste;
          taxitrabajando=true;
          
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
                  
                }else if(direccionx<posicionx){
                  doblar derecha
                  mirando=1;
                  taxitrabajando=true;
                }
              }
          }
        }
      }
    }
  }
  if(direcciony<posiciony){
    if(mirando==4){
      if(calles[posicionx][posiciony].oeste==true){
        scriptseguidor
        lector=blah
        if(lector=!negroC){
          posicionx-1;
          analizar color
          calles[posicionx][posiciony].oeste=colores[0][color].sur;
          calles[posicionx][posiciony].sur=colores[0][color].este;
          calles[posicionx][posiciony].norte=colores[0][color].oeste;
          taxitrabajando=true;
          
        }else if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
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
            
          }else if(direccionx<posicionx){
            doblar derecha
            mirando=1;
            taxitrabajando=true;
            
          }else if(mirando==3){
            if(calles[posicionx][posiciony].norte==true && calles[posicionx][posiciony].sur==false){
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
          }
        }
      }
    }
  }
}
      }
