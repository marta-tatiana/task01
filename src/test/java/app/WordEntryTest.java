package app;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class WordEntryTest {

  @Test
  public void shouldCompareEntriesWithTheSameWord() {
    WordEntry popularity0 = new WordEntry("test");
    WordEntry anotherPopularity0 = new WordEntry("test");
    WordEntry popularity1 = new WordEntry("test");
    popularity1.incrementPopularity();


    assertThat(popularity0.compareTo(popularity1)).isEqualTo(-1);
    assertThat(popularity1.compareTo(popularity0)).isEqualTo(1);
    assertThat(popularity0.compareTo(popularity0)).isEqualTo(0);
    assertThat(popularity0.compareTo(anotherPopularity0)).isEqualTo(0);
  }

  @Test
  public void shouldCompareEntriesWithDifferentWords() {
    WordEntry popularity0 = new WordEntry("test");
    WordEntry anotherPopularity0 = new WordEntry("alsotest");

    WordEntry popularity1 = new WordEntry("testtoo");
    popularity1.incrementPopularity();


    assertThat(popularity0.compareTo(popularity1)).isEqualTo(-1);
    assertThat(popularity1.compareTo(popularity0)).isEqualTo(1);
    assertThat(popularity0.compareTo(popularity0)).isEqualTo(0);
    assertThat(popularity0.compareTo(anotherPopularity0)).isEqualTo(0);
  }
}
