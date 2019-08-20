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

package org.apache.hadoop.yarn.server.scheduler;

import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.webapp.example.MyApp;

/**
 * Composite key for outstanding scheduler requests for any schedulable entity.
 * Currently it includes {@link Priority}.
 */
public final class SchedulerRequestKey implements
    Comparable<SchedulerRequestKey> {

  private final Priority priority;
  private final long allocationRequestId;
  private final ContainerId containerToUpdate;
  //added by Junqing Xiao 18.1.8
  private  MixType mixType = MixType.OFFLINE;

  /**
   * Factory method to generate a SchedulerRequestKey from a ResourceRequest.
   * @param req ResourceRequest
   * @return SchedulerRequestKey
   */
  //added by Junqing Xiao 18.1.8
  public static SchedulerRequestKey create(ResourceRequest req) {
    return new SchedulerRequestKey(req.getPriority(),
        req.getAllocationRequestId(), null,req.getMixType());
  }

  public static SchedulerRequestKey create(UpdateContainerRequest req,
      SchedulerRequestKey schedulerRequestKey) {
    return new SchedulerRequestKey(schedulerRequestKey.getPriority(),
        schedulerRequestKey.getAllocationRequestId(), req.getContainerId(),
            schedulerRequestKey.getMixType());
  }

  /**
   * Convenience method to extract the SchedulerRequestKey used to schedule the
   * Container.
   * @param container Container
   * @return SchedulerRequestKey
   */
  public static SchedulerRequestKey extractFrom(Container container) {
    return new SchedulerRequestKey(container.getPriority(),
        container.getAllocationRequestId(), null
            ,container.getMixType());
  }

  //added by Junqing Xiao 18.1.8
  public SchedulerRequestKey(Priority priority, long allocationRequestId,
                             ContainerId containerToUpdate,MixType mixType) {
    this.priority = priority;
    this.allocationRequestId = allocationRequestId;
    this.containerToUpdate = containerToUpdate;
    if (mixType == null){
      this.mixType = MixType.OFFLINE;
    }else{
      this.mixType = mixType;
    }
  }

  /**
   * Get the {@link Priority} of the request.
   *
   * @return the {@link Priority} of the request
   */
  public Priority getPriority() {
    return priority;
  }

  /**
   * Get the {@link MixType} of the request.
   *
   * @return the {@link MixType} of the request
   */
  public MixType getMixType() {
    return mixType;
  }

  /**
   * Get the Id of the associated {@link ResourceRequest}.
   *
   * @return the Id of the associated {@link ResourceRequest}
   */
  public long getAllocationRequestId() {
    return allocationRequestId;
  }

  public ContainerId getContainerToUpdate() {
    return containerToUpdate;
  }

  @Override
  public int compareTo(SchedulerRequestKey o) {
    if (o == null) {
      return (priority != null) ? -1 : 0;
    } else {
      if (priority == null) {
        return 1;
      }
    }

    // Ensure updates are ranked higher
    if (this.containerToUpdate == null && o.containerToUpdate != null) {
      return -1;
    }
    if (this.containerToUpdate != null && o.containerToUpdate == null) {
      return 1;
    }

    int priorityCompare = o.getPriority().compareTo(priority);
    // we first sort by priority and then by allocationRequestId
    if (priorityCompare != 0) {
      return priorityCompare;
    }
    int allocReqCompare = Long.compare(
        allocationRequestId, o.getAllocationRequestId());

    if (allocReqCompare != 0) {
      return allocReqCompare;
    }

    if (this.containerToUpdate != null && o.containerToUpdate != null) {
      return (this.containerToUpdate.compareTo(o.containerToUpdate));
    }

    //added by Junqing Xiao 18.1.8
    if (mixType == MixType.ONLINE){
      if (o.getMixType() != MixType.ONLINE){
        return 1;
      }
    }else {
      if (o.getMixType() != MixType.OFFLINE){
        return -1;
      }
    }
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SchedulerRequestKey)) {
      return false;
    }

    SchedulerRequestKey that = (SchedulerRequestKey) o;

    if (getAllocationRequestId() != that.getAllocationRequestId()) {
      return false;
    }
    if (!getPriority().equals(that.getPriority())) {
      return false;
    }
    //added by Junqing Xiao 18.1.8
    if (!getMixType().equals(that.getMixType())) {
      return false;
    }
    return containerToUpdate != null ?
        containerToUpdate.equals(that.containerToUpdate) :
        that.containerToUpdate == null;
  }

  @Override
  public int hashCode() {
    int result = priority != null ? priority.hashCode() : 0;
    result = 31 * result + (int) (allocationRequestId ^ (allocationRequestId
        >>> 32));
    result = 31 * result + (containerToUpdate != null ? containerToUpdate
        .hashCode() : 0);
    //added by Junqing Xiao 18.1.8
    result = 31 * result + (mixType != null ? mixType
            .hashCode() : 0);

    return result;
  }

  @Override
  public String toString() {
    return "SchedulerRequestKey{" +
        "priority=" + priority +
        ", allocationRequestId=" + allocationRequestId +
        ", containerToUpdate=" + containerToUpdate +
            ", mixType=" + mixType +
        '}';
  }
}
