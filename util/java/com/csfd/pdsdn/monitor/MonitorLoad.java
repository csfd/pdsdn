/**
 * 
 */
package com.csfd.pdsdn.monitor;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.csfd.pdsdn.helper.GlobalHelper;

/**
 * @author shic
 *
 */
public class MonitorLoad implements Runnable {
   ProcMem mem;
   ProcCpu cpu;
   Sigar sigar;
   long pid;
   /**
    * 
    */
   public MonitorLoad() {
      mem = null;
      sigar = new Sigar();
      pid = sigar.getPid();
      // TODO Auto-generated constructor stub
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {
      long beforeMemLoad = 0;
      long afterMemLoad = 0;
      double beforeCpuLoad = 0;
      double afterCpuLoad = 0;
      int beforeCount = 0;
      int afterCount = 0;

      while (GlobalHelper.monitor) {
         try {
            mem = sigar.getProcMem(pid);
            cpu = sigar.getProcCpu(pid);
         } catch (SigarException se) {
            se.printStackTrace();
         }
         if (false == GlobalHelper.onExecution) {
            beforeMemLoad += mem.getSize();
            beforeCpuLoad += cpu.getPercent();
            beforeCount++;
         } else {
            afterMemLoad += mem.getSize();
            afterCpuLoad += cpu.getPercent();
            afterCount++;
         }
         try {
            Thread.sleep(50);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      System.out.println((afterMemLoad / afterCount - beforeMemLoad / beforeCount) / (beforeMemLoad / beforeCount));
      System.out.println((afterCpuLoad / afterCount - beforeCpuLoad / beforeCount) / (beforeCpuLoad / beforeCount));
   }

}
