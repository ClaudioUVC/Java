taxitrabajando=true;
posicionx=0;
posiciony=0;
mirando=3;
while(taxitrabajando==true){
  if(mirando==3 || mirando==1){
    if(direcciony>posiciony){
      if(mirando==3){
        if(calles[posicionx][posiciony].sur==true){
          scriptseguidor
          lector=blah
          if(lector=!negroC){
            posiciony+1;
            analizar color
            calles[posicionx][posiciony].sur=colores[0][color].sur;
            calles[posicionx][posiciony].este=colores[0][color].este;
            calles[posicionx][posiciony].oeste=colores[0][color].oeste;
            taxitrabajando=true;
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
              
            }else if(mirando==1){
              if(calles[posicionx][posiciony].este==true && calles[posicionx][posiciony].oeste==false){
                doblar derecha
                mirando=2;t
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
          }
        }
      }
    }
    if(direcciony<posiciony){
      if(mirando==1){
        if(calles[posicionx][posiciony].norte==true){
          scriptseguidor
          lector=blah
          if(lector=!negroC){
            posiciony-1;
            analizar color
            calles[posicionx][posiciony].norte=colores[0][color].sur;
            calles[posicionx][posiciony].oeste=colores[0][color].este;
            calles[posicionx][posiciony].este=colores[0][color].oeste;
            taxitrabajando=true;
            
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
