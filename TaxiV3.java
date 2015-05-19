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
        -
      }else if(calles[posicionx][posiciony].pos==false && calles[posicionx][posiciony].pos1==true && calles[posicionx][posiciony].pos3==true){
        
      }else if(calles[posicionx][posiciony].pos==false && calles[posicionx][posiciony].pos1==true && calles[posicionx][posiciony].pos3==false){
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
