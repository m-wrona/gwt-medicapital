package com.medicapital.common.entities;

import java.util.HashMap;
import java.util.Map;

import com.medicapital.common.util.Util;

public enum UrlResourceType {

	Image, Pdf, Word, Excel, Unknown;

	private static final Map<String, UrlResourceType> FILE_TYPES = new HashMap<String, UrlResourceType>();

	static {
		initImageTypes();
		initDocumentTypes();
	}

	private static void initImageTypes() {
		FILE_TYPES.put("jpg", UrlResourceType.Image);
		FILE_TYPES.put("jpeg", UrlResourceType.Image);
		FILE_TYPES.put("png", UrlResourceType.Image);
		FILE_TYPES.put("bmp", UrlResourceType.Image);
		FILE_TYPES.put("tif", UrlResourceType.Image);
	}

	private static void initDocumentTypes() {
		FILE_TYPES.put("pdf", UrlResourceType.Pdf);
		FILE_TYPES.put("doc", UrlResourceType.Word);
		FILE_TYPES.put("docx", UrlResourceType.Word);
		FILE_TYPES.put("xls", UrlResourceType.Excel);
	}

	public static UrlResourceType getType(String type) {
		for (UrlResourceType urlResourceType : UrlResourceType.values()) {
			if (urlResourceType.toString().equalsIgnoreCase(type)) {
				return urlResourceType;
			}
		}
		return UrlResourceType.Unknown;
	}

	public static UrlResourceType getFileType(String fileName) {
		if (!Util.isEmpty(fileName)) {
			for (Map.Entry<String, UrlResourceType> type : FILE_TYPES.entrySet()) {
				if (fileName.endsWith(type.getKey())) {
					return type.getValue();
				}
			}
		}
		return UrlResourceType.Unknown;
	}
}
