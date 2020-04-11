import static org.junit.Assert.assertThrows;
            "new file mode 100544",
    // Make sure file permission is set as specified.
    assertThat(newFile.isReadable()).isTrue();
    assertThat(newFile.isWritable()).isFalse();
    assertThat(newFile.isExecutable()).isTrue();
    newFile.setReadable(true);
    newFile.setWritable(true);
    newFile.setExecutable(true);
    // Make sure file permission is preserved.
    assertThat(newFile.isReadable()).isTrue();
    assertThat(newFile.isWritable()).isTrue();
    assertThat(newFile.isExecutable()).isTrue();
  }

  @Test
  public void testChangeFilePermission() throws IOException, PatchFailedException {
    Path myFile = scratch.file("/root/test.sh", "line one");
    myFile.setReadable(true);
    myFile.setWritable(true);
    myFile.setExecutable(false);
    Path patchFile =
        scratch.file(
            "/root/patchfile",
            "diff --git a/test.sh b/test.sh",
            "old mode 100644",
            "new mode 100755");
    PatchUtil.apply(patchFile, 1, root);
    assertThat(PatchUtil.readFile(myFile)).containsExactly("line one");
    assertThat(myFile.isReadable()).isTrue();
    assertThat(myFile.isWritable()).isTrue();
    assertThat(myFile.isExecutable()).isTrue();
            "diff --git a/ b/",