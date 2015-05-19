import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.*;
import lejos.util.Delay;
import classCelda;

le damos direccion
int direccionx=3;
int direcciony=3;
int posicionx=0;
int posiciony=0;
int mirando=3;
boolean lleguey=false;
boolean lleguex=false;
boolean trabajando=false;
char pos="sur";
if(trabajando==false){
  preguntar direccion.
  while(direccionx!=posicionx || direcciony!=posiciony){
    trabajando=true;
    while(trabajando){
      if(mirando==1){
        pos="norte";
        pos+1="este";
        pos+2="sur";
        pos+3="oeste";
      }else if(mirando==2){
        pos="este";
        pos+1="sur";
        pos+2="oeste";
        pos+3="norte";
      }else if(mirando==3){
        pos="sur";
        pos+1="oeste";
        pos+2="norte";
        pos+3="este";
      }else if(mirando==4){
        pos="oeste";
        pos+1="norte";
        pos+2="este";
        pos+3="sur";
      }
      
    }
  }
}
