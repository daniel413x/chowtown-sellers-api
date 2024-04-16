package com.api.cloudinary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryPOSTReq {

    private FilePart file;
};