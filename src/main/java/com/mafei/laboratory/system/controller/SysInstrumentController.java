package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.service.SysInstrumentService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/**
 * 仪器信息表(SysInstrument)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:38:26
 */
@RestController
@RequestMapping("/api/instrument")
@RequiredArgsConstructor
public class SysInstrumentController {
    private final SysInstrumentService instrumentService;
    private static final String UPLOAD_DIR = "uploads/instruments/";

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(instrumentService.findAll());
    }

    @GetMapping("/repair")
    public ResponseEntity<Object> findAllRepair() {
        return ResponseEntity.ok(instrumentService.findAllRepair());
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.write(path, file.getBytes());

            // 返回文件访问路径
            return ResponseEntity.ok("/uploads/instruments/" + filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件上传失败：" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@RequestBody InstrumentVo instrumentVo) {
        instrumentService.insert(instrumentVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody InstrumentVo instrumentVo) {
        instrumentService.update(instrumentVo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        System.out.println("PATCH 请求已接收: " + json);
        instrumentService.updateStatus(json.getStatus(), json.getIds());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        instrumentService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        instrumentService.deleteByIds(ids);
    }
}
