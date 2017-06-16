package de.espend.idea.php.phpunit.tests.type;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.elements.Method;
import de.espend.idea.php.phpunit.tests.PhpUnitLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see de.espend.idea.php.phpunit.type.SetUpTypeProvider
 */
public class SetUpTypeProviderTest extends PhpUnitLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("classes.php");
        myFixture.copyFileToProject("ProphecyTypeProvider.php");
        myFixture.copyFileToProject("SetUpTypeProvider.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatSetUpTypesForFieldReferencesAreProvided() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE, "<?php" +
            "    class FooTest extends \\PHPUnit\\Framework\\TestCase\n" +
            "    {\n" +
            "        public function setUp()\n" +
            "        {\n" +
            "            $this->fake = $this->prophesize(\\Bar::class);\n" +
            "        }\n" +
            "\n" +
            "        public function itShouldDoFoobar()\n" +
            "        {\n" +
            "            $this->fake->getFo<caret>obar();\n" +
            "        }\n" +
            "    }",
            PlatformPatterns.psiElement(Method.class).withName("getFoobar")
        );

        assertPhpReferenceResolveTo(PhpFileType.INSTANCE, "<?php" +
                "    class FooTest extends \\PHPUnit\\Framework\\TestCase\n" +
                "    {\n" +
                "        public function setUp()\n" +
                "        {\n" +
                "            $this->fake = $this->createMock(\\Bar::class);\n" +
                "        }\n" +
                "\n" +
                "        public function itShouldDoFoobar()\n" +
                "        {\n" +
                "            $this->fake->getFo<caret>obar();\n" +
                "        }\n" +
                "    }",
            PlatformPatterns.psiElement(Method.class).withName("getFoobar")
        );
    }

    public void testThatSetUpTypesForFieldReferencesWithMultipleAssignmentsAreProvided() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE, "<?php" +
                "    class FooTest extends \\PHPUnit\\Framework\\TestCase\n" +
                "    {\n" +
                "        public function setUp()\n" +
                "        {\n" +
                "            $this->fake = $this->prophesize(\\Bar::class);\n" +
                "        }\n" +
                "\n" +
                "        public function itShouldDoFoobar()\n" +
                "        {\n" +
                "            $this->fake->getFo<caret>obar();\n" +
                "        }\n" +
                "    }",
            PlatformPatterns.psiElement(Method.class).withName("getFoobar")
        );
    }
}
