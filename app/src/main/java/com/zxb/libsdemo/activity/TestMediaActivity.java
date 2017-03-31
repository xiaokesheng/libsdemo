package com.zxb.libsdemo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.AudioCodec;
import com.zxb.libsdemo.util.Util;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mrzhou on 17/3/10.
 */
public class TestMediaActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "TestMediaActivity";

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private MediaExtractor mMediaExtractor;
    private MediaMuxer mMediaMuxer;

    TextView tvExtractor;
    TextView tvMuxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_mediatest);

        tvExtractor = (TextView) findViewById(R.id.tvExtractor);
        tvMuxer = (TextView) findViewById(R.id.tvMuxer);

        Util.setClickListener(this, tvExtractor, tvMuxer);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvExtractor:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            extract();
                        } catch (Exception e) {
                            Log.e(TAG, "Exception");
                        }
                    }
                }).start();
                break;
            case R.id.tvMuxer:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            muxer();
                        } catch (Exception e) {
                            Log.e(TAG, "Exception muxer");
                        }
                    }
                }).start();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void extract() throws IOException {
        mMediaExtractor = new MediaExtractor();
        mMediaExtractor.setDataSource(SDCARD_PATH + "/lightmoon.mp4");

        int mVideoTrackIndex = -1;
        int framerate = 0;
        for (int i = 0; i < mMediaExtractor.getTrackCount(); i++) {
            MediaFormat format = mMediaExtractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            Log.e(TAG, "mime: " + mime);
            if (!mime.startsWith("video/")) {
                continue;
            }
            framerate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
            mMediaExtractor.selectTrack(i);
            mMediaMuxer = new MediaMuxer(SDCARD_PATH + "/ouput_lightmoon.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            mVideoTrackIndex = mMediaMuxer.addTrack(format);
            mMediaMuxer.start();
        }

        if (mMediaMuxer == null) {
        }

        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        info.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(500 * 1024);
        while (true) {
            int sampleSize = mMediaExtractor.readSampleData(buffer, 0);
            if (sampleSize < 0) {
                break;
            }
            mMediaExtractor.advance();
            info.offset = 0;
            info.size = sampleSize;
            info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
            info.presentationTimeUs += 1000 * 1000 / framerate;
            mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
        }

        mMediaExtractor.release();

        mMediaMuxer.stop();
        mMediaMuxer.release();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void muxer() {
//        transac();
        realMuxer();

    }

    private void transac() {
        final AudioCodec audioCodec = AudioCodec.newInstance();
        audioCodec.setEncodeType(MediaFormat.MIMETYPE_AUDIO_MPEG);
        audioCodec.setIOPath(SDCARD_PATH + "/puremusic.mp3", SDCARD_PATH + "/puremusic.aac");
        audioCodec.prepare();
        audioCodec.startAsync();
        audioCodec.setOnCompleteListener(new AudioCodec.OnCompleteListener() {
            @Override
            public void completed() {
                audioCodec.release();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void realMuxer() {
        try {
            mMediaExtractor = new MediaExtractor();
            mMediaExtractor.setDataSource(SDCARD_PATH + "/liudong.m4a");
            int mMusicTrackIndex = -1;
            int framerate = 0;
            MediaFormat format = mMediaExtractor.getTrackFormat(0);
            Log.e(TAG, "mime: " + format.getString(MediaFormat.KEY_MIME));

//            framerate = format.getInteger(MediaFormat.KEY_BIT_RATE);
            mMediaExtractor.selectTrack(0);
            mMediaMuxer = new MediaMuxer(SDCARD_PATH + "/ouput_lightmoon.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            mMusicTrackIndex = mMediaMuxer.addTrack(format);
            mMediaMuxer.start();

            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(500 * 1024);
            while (true) {
                int sampleSize = mMediaExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                mMediaExtractor.advance();
                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
//                info.presentationTimeUs += 1000 * 1000 / framerate;
                mMediaMuxer.writeSampleData(mMusicTrackIndex, buffer, info);
            }

            mMediaExtractor.release();

            mMediaMuxer.stop();
            mMediaMuxer.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
