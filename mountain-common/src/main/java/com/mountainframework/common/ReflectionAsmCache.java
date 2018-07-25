package com.mountainframework.common;

import java.util.Map;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class ReflectionAsmCache {

	private Map<Class<?>, MethodAccess> cachedAsm;

	private ReflectionAsmCache() {
	}

	private ReflectionAsmCache(Map<Class<?>, MethodAccess> cacheAsm) {
		this.cachedAsm = cacheAsm;
	}

	public static ReflectionAsmCacheBuilder builder() {
		return new ReflectionAsmCacheBuilder();
	}

	public MethodAccess get(final Class<?> type) {
		return Preconditions.checkNotNull(cachedAsm.get(type), "Get cache methodAccess is null");
	}

	public MethodAccess getUnchecked(final Class<?> type) {
		return cachedAsm.get(type);
	}

	public static class ReflectionAsmCacheBuilder {

		private Map<Class<?>, MethodAccess> cachedMap = Maps.newConcurrentMap();

		public void loadCache(Class<?> cls) {
			cachedMap.put(cls, MethodAccess.get(cls));
		}

		public void loadCache(Class<?>[] clses) {
			for (Class<?> cls : clses) {
				cachedMap.put(cls, MethodAccess.get(cls));
			}
		}

		public ReflectionAsmCache build() {
			return new ReflectionAsmCache(cachedMap);
		}
	}

}
