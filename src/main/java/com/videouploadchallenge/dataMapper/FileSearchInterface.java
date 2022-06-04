package com.videouploadchallenge.dataMapper;

import java.time.LocalDateTime;

public interface FileSearchInterface {
    public long getFileid();
    public LocalDateTime getCreated_at();
    public String getName();
    public int getSize();
}
