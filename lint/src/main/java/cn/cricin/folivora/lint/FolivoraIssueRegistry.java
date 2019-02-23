/*
 * Copyright (C) 2019 Cricin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.cricin.folivora.lint;

import cn.cricin.folivora.dom.FolivoraDomExtender;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class FolivoraIssueRegistry extends IssueRegistry {

  static {
    initDomExtender();
  }

  private static void initDomExtender() {
    boolean lintInsideStudio;
    try {
      Class.forName("org.jetbrains.android.dom.AndroidDomElement");
      lintInsideStudio = true;
    } catch (ClassNotFoundException e) {
      lintInsideStudio = false;
    }
    if (lintInsideStudio) {
      try {
        FolivoraDomExtender.install();
      } catch (Exception ignore) {}
    }
  }

  @NotNull
  @Override
  public List<Issue> getIssues() {
    return Arrays.asList(
      InstalledBeforeSuperDetector.ISSUE,
      InternalFolivoraApiDetector.ISSUE,
      PreferStubViewDetector.ISSUE
    );
  }

  @Override
  public int getApi() {
    return ApiKt.CURRENT_API;
  }
}
