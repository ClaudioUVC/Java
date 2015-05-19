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
        }
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
