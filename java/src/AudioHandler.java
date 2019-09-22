import javax.sound.sampled.*;
import java.io.File;
import java.util.Arrays;

public class AudioHandler {

    public Mixer.Info[] getMixersInfo() {
        return AudioSystem.getMixerInfo();
    }

    public String[] mixerInfoToString() {
        Mixer.Info[] mixerInfo = getMixersInfo();
        String[] result = new String[mixerInfo.length];
        for (int i = 0; i < mixerInfo.length; i++) {
            result[i] = mixerInfo[i].getName();
        }
        return result;
    }

    public void playSound(String fileName, String micName) {
        Mixer mixer = getMixer(micName);
        if (mixer == null) {
            return;
        }
        File file = new File(fileName);
        SourceDataLine source = null;
        AudioInputStream stream = null;
        byte[] buffer = null;
        try {
            stream = AudioSystem.getAudioInputStream(file);
            System.out.println(stream.getFormat().toString());
            System.out.println(Arrays.toString(mixer.getSourceLineInfo()));
            System.out.println(Arrays.toString(mixer.getTargetLineInfo()));
            source = AudioSystem.getSourceDataLine(stream.getFormat(), mixer.getMixerInfo());
            System.out.println(Arrays.toString(mixer.getSourceLineInfo()));
            source.open(stream.getFormat());
            buffer = stream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        source.start();
        source.write(buffer, 0, buffer.length);
        source.drain();
        source.close();
    }

    private Mixer getMixer(String micName) {
        for (Mixer.Info info: AudioSystem.getMixerInfo()) {
            if (info.getName().equals(micName)) {
                return AudioSystem.getMixer(info);
            }
        }
        return null;
    }
}