package com.medicapital.client.pages;

abstract class PageFactory {

	/**
	 * Create page of given class
	 * 
	 * @param pageClass
	 * @return instance of page or null if page is nut supported by this factory
	 */
	abstract <T extends Page<?>> Page<?> createPage(Class<T> pageClass);

	/**
	 * Create page of given name
	 * 
	 * @param pageName
	 * @return instance of page or null if page is nut supported by this factory
	 */
	abstract Page<?> createPage(String pageName);

	/**
	 * Get name of the page
	 * 
	 * @param page
	 * @return
	 */
	public static <T extends Page<?>> String getPageName(Class<T> page) {
		String fullClassName = page.getName();
		int lastDotIndex = fullClassName.lastIndexOf(".");
		return fullClassName.substring(lastDotIndex + 1);
	}
}
