/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.yarn.api.protocolrecords;

import java.util.List;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.yarn.api.ApplicationMasterProtocol;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.util.Records;

/**
 * <p>The core request sent by the <code>ApplicationMaster</code> to the 
 * <code>ResourceManager</code> to obtain resources in the cluster.</p> 
 *
 * <p>The request includes:
 * <ul>
 *   <li>A response id to track duplicate responses.</li>
 *   <li>Progress information.</li>
 *   <li>
 *     A list of {@link ResourceRequest} to inform the
 *     <code>ResourceManager</code> about the application's
 *     resource requirements.
 *   </li>
 *   <li>
 *     A list of unused {@link Container} which are being returned.
 *   </li>
 *   <li>
 *     A list of {@link UpdateContainerRequest} to inform
 *     the <code>ResourceManager</code> about the change in
 *     requirements of running containers.
 *   </li>
 * </ul>
 * 
 * @see ApplicationMasterProtocol#allocate(AllocateRequest)
 */
@Public
@Stable
public abstract class AllocateRequest {

  @Public
  @Stable
  public static AllocateRequest newInstance(int responseID, float appProgress,
      List<ResourceRequest> resourceAsk,
      List<ContainerId> containersToBeReleased,
      ResourceBlacklistRequest resourceBlacklistRequest) {
    return AllocateRequest.newBuilder().responseId(responseID)
        .progress(appProgress).askList(resourceAsk)
        .releaseList(containersToBeReleased)
        .resourceBlacklistRequest(resourceBlacklistRequest).build();
  }
  
  @Public
  @Unstable
  public static AllocateRequest newInstance(int responseID, float appProgress,
      List<ResourceRequest> resourceAsk,
      List<ContainerId> containersToBeReleased,
      List<UpdateContainerRequest> updateRequests,
      ResourceBlacklistRequest resourceBlacklistRequest) {
    return AllocateRequest.newBuilder().responseId(responseID)
        .progress(appProgress).askList(resourceAsk)
        .releaseList(containersToBeReleased)
        .resourceBlacklistRequest(resourceBlacklistRequest)
        .updateRequests(updateRequests)
        .build();
  }
  
  /**
   * Get the <em>response id</em> used to track duplicate responses.
   * @return <em>response id</em>
   */
  @Public
  @Stable
  public abstract int getResponseId();

  /**
   * Set the <em>response id</em> used to track duplicate responses.
   * @param id <em>response id</em>
   */
  @Public
  @Stable
  public abstract void setResponseId(int id);

  /**
   * Get the <em>current progress</em> of application. 
   * @return <em>current progress</em> of application
   */
  @Public
  @Stable
  public abstract float getProgress();
  
  /**
   * Set the <em>current progress</em> of application
   * @param progress <em>current progress</em> of application
   */
  @Public
  @Stable
  public abstract void setProgress(float progress);

  /**
   * Get the list of <code>ResourceRequest</code> to update the 
   * <code>ResourceManager</code> about the application's resource requirements.
   * @return the list of <code>ResourceRequest</code>
   * @see ResourceRequest
   */
  @Public
  @Stable
  public abstract List<ResourceRequest> getAskList();
  
  /**
   * Set list of <code>ResourceRequest</code> to update the
   * <code>ResourceManager</code> about the application's resource requirements.
   * @param resourceRequests list of <code>ResourceRequest</code> to update the 
   *                        <code>ResourceManager</code> about the application's 
   *                        resource requirements
   * @see ResourceRequest
   */
  @Public
  @Stable
  public abstract void setAskList(List<ResourceRequest> resourceRequests);

  /**
   * Get the list of <code>ContainerId</code> of containers being 
   * released by the <code>ApplicationMaster</code>.
   * @return list of <code>ContainerId</code> of containers being 
   *         released by the <code>ApplicationMaster</code> 
   */
  @Public
  @Stable
  public abstract List<ContainerId> getReleaseList();

  /**
   * Set the list of <code>ContainerId</code> of containers being
   * released by the <code>ApplicationMaster</code>
   * @param releaseContainers list of <code>ContainerId</code> of 
   *                          containers being released by the 
   *                          <code>ApplicationMaster</code>
   */
  @Public
  @Stable
  public abstract void setReleaseList(List<ContainerId> releaseContainers);
  
  /**
   * Get the <code>ResourceBlacklistRequest</code> being sent by the 
   * <code>ApplicationMaster</code>.
   * @return the <code>ResourceBlacklistRequest</code> being sent by the 
   *         <code>ApplicationMaster</code>
   * @see ResourceBlacklistRequest
   */
  @Public
  @Stable
  public abstract ResourceBlacklistRequest getResourceBlacklistRequest();
  
  /**
   * Set the <code>ResourceBlacklistRequest</code> to inform the 
   * <code>ResourceManager</code> about the blacklist additions and removals
   * per the <code>ApplicationMaster</code>.
   * 
   * @param resourceBlacklistRequest the <code>ResourceBlacklistRequest</code>  
   *                         to inform the <code>ResourceManager</code> about  
   *                         the blacklist additions and removals
   *                         per the <code>ApplicationMaster</code>
   * @see ResourceBlacklistRequest
   */
  @Public
  @Stable
  public abstract void setResourceBlacklistRequest(
      ResourceBlacklistRequest resourceBlacklistRequest);
  
  /**
   * Get the list of container update requests being sent by the
   * <code>ApplicationMaster</code>.
   * @return list of {@link UpdateContainerRequest}
   *         being sent by the
   *         <code>ApplicationMaster</code>.
   */
  @Public
  @Unstable
  public abstract List<UpdateContainerRequest> getUpdateRequests();

  //added by junqing xiao 18.4.16
  @Public
  @Unstable
  public abstract List<ContainerScore> getScores();

  /**
   * Set the list of container update requests to inform the
   * <code>ResourceManager</code> about the containers that need to be
   * updated.
   * @param updateRequests list of <code>UpdateContainerRequest</code> for
   *                       containers to be updated
   */
  @Public
  @Unstable
  public abstract void setUpdateRequests(
      List<UpdateContainerRequest> updateRequests);

  //added by junqing xiao 18.4.16
  @Public
  @Unstable
  public abstract void setScores(
          List<ContainerScore> scores);

  @Public
  @Unstable
  public static AllocateRequestBuilder newBuilder() {
    return new AllocateRequestBuilder();
  }

  /**
   * Class to construct instances of {@link AllocateRequest} with specific
   * options.
   */
  @Public
  @Stable
  public static final class AllocateRequestBuilder {
    private AllocateRequest allocateRequest =
        Records.newRecord(AllocateRequest.class);

    private AllocateRequestBuilder() {
    }

    /**
     * Set the <code>responseId</code> of the request.
     * @see AllocateRequest#setResponseId(int)
     * @param responseId <code>responseId</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Stable
    public AllocateRequestBuilder responseId(int responseId) {
      allocateRequest.setResponseId(responseId);
      return this;
    }

    /**
     * Set the <code>progress</code> of the request.
     * @see AllocateRequest#setProgress(float)
     * @param progress <code>progress</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Stable
    public AllocateRequestBuilder progress(float progress) {
      allocateRequest.setProgress(progress);
      return this;
    }

    /**
     * Set the <code>askList</code> of the request.
     * @see AllocateRequest#setAskList(List)
     * @param askList <code>askList</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Stable
    public AllocateRequestBuilder askList(List<ResourceRequest> askList) {
      allocateRequest.setAskList(askList);
      return this;
    }

    /**
     * Set the <code>releaseList</code> of the request.
     * @see AllocateRequest#setReleaseList(List)
     * @param releaseList <code>releaseList</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Stable
    public AllocateRequestBuilder releaseList(List<ContainerId> releaseList) {
      allocateRequest.setReleaseList(releaseList);
      return this;
    }

    /**
     * Set the <code>resourceBlacklistRequest</code> of the request.
     * @see AllocateRequest#setResourceBlacklistRequest(
     * ResourceBlacklistRequest)
     * @param resourceBlacklistRequest
     *     <code>resourceBlacklistRequest</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Stable
    public AllocateRequestBuilder resourceBlacklistRequest(
        ResourceBlacklistRequest resourceBlacklistRequest) {
      allocateRequest.setResourceBlacklistRequest(resourceBlacklistRequest);
      return this;
    }

    /**
     * Set the <code>updateRequests</code> of the request.
     * @see AllocateRequest#setUpdateRequests(List)
     * @param updateRequests <code>updateRequests</code> of the request
     * @return {@link AllocateRequestBuilder}
     */
    @Public
    @Unstable
    public AllocateRequestBuilder updateRequests(
        List<UpdateContainerRequest> updateRequests) {
      allocateRequest.setUpdateRequests(updateRequests);
      return this;
    }

    //added by junqing xiao 18.4.16
    @Public
    @Unstable
    public AllocateRequestBuilder containerScores(
            List<ContainerScore> scores) {
      allocateRequest.setScores(scores);
      return this;
    }

    /**
     * Return generated {@link AllocateRequest} object.
     * @return {@link AllocateRequest}
     */
    @Public
    @Stable
    public AllocateRequest build() {
      return allocateRequest;
    }
  }
}