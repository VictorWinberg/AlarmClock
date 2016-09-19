package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	
	private int currentTime, alarmTime, alarms;
	
	private ClockInput input;
	private ClockOutput output;

	private MutexSem sem;
	
	public SharedData(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = new MutexSem();
	}

	public void addSecond() {
		sem.take();
		if(input.getChoice() != ClockInput.SET_TIME)
			currentTime++;
		
		if(input.getChoice() == ClockInput.SHOW_TIME && input.getAlarmFlag() && currentTime == alarmTime) {
			alarms = 20;
		}			
		
        if(alarms > 0) {
        	output.doAlarm();
        	alarms--;
        }
        
        int hhmmss = convertIntoHHMMSS(currentTime);        
        output.showTime(hhmmss);        	
        sem.give();
	}
	
	private int convertIntoHHMMSS(int seconds) {
		int hh = seconds / (60 * 60);
        int mm = (seconds / 60) % 60;
        int ss = seconds % 60;
        return hh * 10000 + mm * 100 + ss;
	}
	
	private int convertIntoSeconds(int hhmmss) {
		int hh = hhmmss / 10000;
        int mm = (hhmmss / 100) % 100;
        int ss = hhmmss % 100;
        return hh * 60 * 60 + mm * 60 + ss;
	}

	public void setAlarm(int hhmmss) {
		alarmTime = convertIntoSeconds(hhmmss);
	}
	
	public void stopAlarm() {
		alarms = 0;
	}

	public void setTime(int hhmmss) {
		currentTime = convertIntoSeconds(hhmmss);
	}
}
