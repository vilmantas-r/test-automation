/**
 * Copyright (c) 2022 Nortal AS
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.nortal.test.selenide.hook

import com.nortal.test.core.services.hooks.BeforeSuiteHook
import com.nortal.test.core.util.SeleniumRemoteProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component

@Component
class SelenideRemoteChromePrepHook(private val seleniumRemoteProvider: ObjectProvider<SeleniumRemoteProvider>) :
    BeforeSuiteHook {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun beforeSuite() {
        seleniumRemoteProvider.ifAvailable {
            log.info(
                "Selenium remote provider is available. Configuring selenide to use {} as a remote.",
                it.getRemoteUrl()
            )

            com.codeborne.selenide.Configuration.remote = it.getRemoteUrl()
        }
    }
}