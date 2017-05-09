/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.underfs;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Information about a file or a directory in the under file system. Listing contents in a
 * {@link UnderFileSystem} returns entries of this class.
 */
@NotThreadSafe
public class UnderFileStatus {
  /** Size of a directory. */
  public static final long DIRECTORY_CONTENT_LENGTH = 0L;

  /** Last modified time for a directory. */
  public static final long INVALID_MODIFIED_TIME = 0L;

  private long mContentLength;
  private boolean mIsDirectory;
  private long mLastModifiedTimeMs;
  private String mName;

  // Permissions
  private String mOwner;
  private String mGroup;
  private short mMode;

  /**
   * Creates new instance for under file information.
   *
   * @param name relative path of file or directory
   * @param contentLength in bytes
   * @param isDirectory whether the path is a directory
   * @param lastModifiedTimeMs UTC time
   * @param owner of the file
   * @param group of the file
   * @param mode of the file
   */
  public UnderFileStatus(String name, long contentLength, boolean isDirectory,
      long lastModifiedTimeMs, String owner, String group, short mode) {
    mContentLength = contentLength;
    mIsDirectory = isDirectory;
    mLastModifiedTimeMs = lastModifiedTimeMs;
    mName = name;
    mOwner = owner;
    mGroup = group;
    mMode = mode;
  }

  /**
   * @return true, if the path is a directory
   */
  public boolean isDirectory() {
    return mIsDirectory;
  }

  /**
   * @return true, if the path is a file
   */
  public boolean isFile() {
    return !mIsDirectory;
  }

  /**
   * Get the content size in bytes.
   *
   * @return if a file, file size in bytes; otherwise, 0
   */
  public long getContentLength() {
    return mContentLength;
  }

  /**
   * Gets the group of the given path.
   *
   * @return the group of the file
   */
  public String getGroup() {
    return mGroup;
  }

  /**
   * Gets the UTC time of when the indicated path was modified recently in ms.
   *
   * @return modification time in milliseconds
   */
  public long getLastModifiedTime() {
    return mLastModifiedTimeMs;
  }

  /**
   * Gets the mode of the given path in short format, e.g 0700.
   *
   * @return the mode of the file
   */
  public short getMode() {
    return mMode;
  }

  /**
   * @return name of file or directory
   */
  public String getName() {
    return mName;
  }

  /**
   * Gets the owner of the given path.
   *
   * @return the owner of the path
   */
  public String getOwner() {
    return mOwner;
  }

  /**
   * Set the name of file or directory.
   *
   * @param name of entry
   * @return this object
   */
  public UnderFileStatus setName(String name) {
    mName = name;
    return this;
  }

  @Override
  public String toString() {
    return getName();
  }

  /**
   * Converts an array of UFS file status to a listing result where each element in the array is
   * a file or directory name.
   *
   * @param children array of listing statuses
   * @return array of file or directory names, or null if the input is null
   */
  public static String[] convertToNames(UnderFileStatus[] children) {
    if (children == null) {
      return null;
    }
    String[] ret = new String[children.length];
    for (int i = 0; i < children.length; ++i) {
      ret[i] = children[i].getName();
    }
    return ret;
  }
}
