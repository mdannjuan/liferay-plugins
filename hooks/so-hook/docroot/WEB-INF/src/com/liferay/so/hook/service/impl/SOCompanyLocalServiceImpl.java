/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.so.hook.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceWrapper;
import com.liferay.portlet.PortletPreferencesThreadLocal;
import com.liferay.so.util.InstanceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SOCompanyLocalServiceImpl extends CompanyLocalServiceWrapper {

	public SOCompanyLocalServiceImpl(CompanyLocalService companyLocalService) {
		super(companyLocalService);
	}

	@Override
	public Company checkCompany(String webId)
		throws PortalException, SystemException {

		Company company = super.checkCompany(webId);

		boolean strict = PortletPreferencesThreadLocal.isStrict();

		try {
			PortletPreferencesThreadLocal.setStrict(false);

			InstanceUtil.initRuntime(company.getCompanyId());

			if (InstanceUtil.isInitialized(company.getCompanyId())) {
				return company;
			}

			InstanceUtil.initInstance(company.getCompanyId());
		}
		finally {
			PortletPreferencesThreadLocal.setStrict(strict);
		}

		return company;
	}

}